package com.inavan.cadastro.service;

import com.inavan.cadastro.dto.ItemBuscaDto;
import com.inavan.cadastro.dto.ProdutoComIdDto;
import com.inavan.cadastro.dto.ProdutoDto;
import com.inavan.cadastro.entidade.produto.Produto;
import com.inavan.cadastro.exception.ProdutoNaoEncontratoException;
import com.inavan.cadastro.helper.CopiaHelper;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class ProdutoService {
    public Uni<ProdutoComIdDto> incluirOuAlterar(ProdutoDto produtoDigitado) {
        var q = Produto.acharPorNome(produtoDigitado.getNome());
        var px = q
                .onItem()
                .transformToUni(p -> {
                    log.debug("2. Obtido {}", p);
                    var p2 = copiarDados(p, produtoDigitado);
                    log.debug("3. Vai gravar {}", p2);
                    return p2.persistOrUpdate()
                            .onItem()
                            .castTo(Produto.class)
                            .map(ProdutoComIdDto::new)
                            .invoke(pp -> log.debug("4. Gravou {}", pp));
                })
                .onFailure(err -> {
                    log.error("Erro inserindo", err);
                    return true;
                })
                .recoverWithNull()
                ;
        log.debug("1. Fim da inserção no serviço");
        return px;
    }

    private Produto copiarDados(Produto produtoObtido, ProdutoDto produtoDigitado) {
        final Produto prodGravar = new Produto();
        if (produtoObtido == null) {
            CopiaHelper.copiar(prodGravar, produtoDigitado);
            prodGravar.id = null;
        } else {
            ObjectId id = produtoObtido.id;
            CopiaHelper.copiar(prodGravar, produtoDigitado);
            prodGravar.id = id;
        }
        return prodGravar;
    }

    public Uni<ProdutoComIdDto> alterar(String idStr, ProdutoDto param) {
        ObjectId id = new ObjectId(idStr);
        Uni<Produto> px = Produto.findById(id);
        var res = px
                .onItem()
                .ifNull()
                .failWith(ProdutoNaoEncontratoException::new)
                .onItem()
                .transformToUni(prod -> {
                    ObjectId idX = id;
                    CopiaHelper.copiar(prod, param);
                    prod.id = idX;
                    return prod.update()
                            .onItem()
                            .castTo(Produto.class)
                            .map(ProdutoComIdDto::new)
                            .invoke(pp -> log.debug("Alterou para {}", pp));
                });
        return res;
    }
    public Uni<List<ProdutoComIdDto>> lerPorNome(String nome, int pagina, int tamanho) {
        return Produto.acharTodosPorNome(nome, pagina, tamanho)
                .onItem()
                .transform(lista -> lista.stream()
                            .map(ProdutoComIdDto::new)
                            .collect(Collectors.toList()));
    }

    public Uni<ProdutoComIdDto> ler(String idStr) {
        ObjectId id = new ObjectId(idStr);
        return Produto.findById(id)
                .onItem()
                .ifNull().failWith(new ProdutoNaoEncontratoException())
                .onItem()
                .castTo(Produto.class)
                .onItem()
                .transform(ProdutoComIdDto::new);
    }

    public Uni<List<ProdutoComIdDto>> listar(int pagina, int tamanho) {
        log.debug("procurando... listagem");
        var q = Produto.acharTodos(pagina, tamanho);
        var res = q
                .onItem()
                .ifNotNull()
                .transform(ProdutoComIdDto::paraListaObjetos)
                .onFailure(err -> {
                    log.error("Erro ao listar", err);
                    return true;
                })
                .recoverWithItem(Collections.emptyList())
                ;
        log.debug("Retornando");
        return res;
    }

    public Uni<List<ItemBuscaDto>> listarTodosOsNomes() {
        return Produto.aTodos()
                .list()
                .onItem()
                .ifNotNull()
                .transform(this::paraListaItem);
    }

    private List<ItemBuscaDto> paraListaItem(List<Produto> produtos) {
        return produtos.stream()
                .map(ItemBuscaDto::new)
                .collect(Collectors.toList());
    }

    public void excluir(String idStr) {
        ObjectId id = new ObjectId(idStr);
        Uni<Produto> px = Produto.findById(id);
        var res = px.onItem()
                .invoke(Produto::delete)
                .ifNoItem()
                        .after(Duration.ofSeconds(3))
                                .failWith(ProdutoNaoEncontratoException::new);
        log.debug("na fila de exclusão: {}.", idStr);
    }

}
