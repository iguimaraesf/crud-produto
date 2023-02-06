package com.inavan.cadastro.service;

import com.inavan.cadastro.dto.ItemBuscaDto;
import com.inavan.cadastro.dto.ProdutoComIdDto;
import com.inavan.cadastro.dto.ProdutoDto;
import com.inavan.cadastro.entidade.produto.Produto;
import com.inavan.cadastro.exception.ProdutoNaoEncontratoException;
import com.inavan.cadastro.helper.CopiaHelper;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class ProdutoService {
    public Uni<?> incluirOuAlterar(Produto param) {
        var q = Produto.acharPorNome(param.nome);
        var px = q
                .onItem()
                .transformToUni(p -> {
                    log.debug("2. Obtido {}", p);
                    var p2 = copiarDados(p, param);
                    log.debug("3. Vai gravar {}", p2);
                    return p2.persistOrUpdate()
                            .onItem()
                            .castTo(Produto.class)
                            //.transform(pp -> (Produto) pp)
                            .invoke(pp -> log.debug("4. Gravou {}", pp));
                })
                .onFailure(err -> {
                    log.error("Erro inserindo", err);
                    return true;
                })
                .recoverWithNull()
                ;
        log.debug("1. Fim da inserção no serviço");
        //return new ProdutoComIdDto(p);
        return px;
    }

    private Produto copiarDados(Produto p, Produto param) {
        final Produto prodGravar = new Produto();
        if (p == null) {
            //param.copiarParaEntidade(p);
            CopiaHelper.copiar(prodGravar, param);
            prodGravar.id = null;
//                        p.persist();
        } else {
            ObjectId id = p.id;
            CopiaHelper.copiar(prodGravar, param);
            prodGravar.id = id;
//                        p.update();
            //param.adicionarNasListas(p);
        }
        return prodGravar;
    }

    public Uni<?> alterar(String idStr, Produto param) {
        ObjectId id = new ObjectId(idStr);
        Uni<Produto> px = Produto.findById(id);
        var res = px
                .onItem()
                .ifNotNull()
                .transformToUni(prod -> {
                    ObjectId idX = id;
                    CopiaHelper.copiar(prod, param);
                    prod.id = idX;
                    return prod.update()
                            .onItem()
                            .transform(pp -> (Produto) pp)
                            .invoke(pp -> log.debug("Alterou para {}", pp));
                });
        return res;
    }
    public Uni<List<Produto>> lerPorNome(String nome, int pagina, int tamanho) {
        return Produto.acharTodosPorNome(nome, pagina, tamanho);
        //return ProdutoComIdDto.paraListaObjetos(lista);
    }

    public Uni<Produto> ler(String idStr) {
        ObjectId id = new ObjectId(idStr);
        return Produto.findById(id);
//        if (p == null) {
//            throw new ProdutoNaoEncontratoException();
//        }
//        return new ProdutoComIdDto(p);
    }

    public Uni<List<Produto>> listar(int pagina, int tamanho) {
        log.debug("procurando... listagem");
        var q = Produto.acharTodos(pagina, tamanho);
        var res = q
                .onItem()
                .ifNotNull()
                .transform(this::paraListaObjetos)
                .onFailure(err -> {
                    log.error("Erro ao listar", err);
                    return true;
                })
                .recoverWithItem(Collections.emptyList())
                ;
        log.debug("Retornando");
        return res;
//        var res = ProdutoComIdDto.paraListaObjetos(lista);
//        log.debug(res.toString());
//        return res;
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
    private List<Produto> paraListaObjetos(List<Produto> produtos) {
        return produtos;
    }

    public void excluir(String idStr) {
        ObjectId id = new ObjectId(idStr);
        Uni<Produto> px = Produto.findById(id);
        var res = px.onItem().invoke(ReactivePanacheMongoEntityBase::delete);
        log.debug("na fila: {}.", res);
    }

}
