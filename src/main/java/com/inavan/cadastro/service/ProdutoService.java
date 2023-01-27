package com.inavan.cadastro.service;

import com.inavan.cadastro.dto.ItemBuscaDto;
import com.inavan.cadastro.dto.ProdutoComIdDto;
import com.inavan.cadastro.dto.ProdutoDto;
import com.inavan.cadastro.entidade.produto.Produto;
import com.inavan.cadastro.exception.ProdutoNaoEncontratoException;
import com.inavan.cadastro.helper.CopiaHelper;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class ProdutoService {
    public Produto incluirOuAlterar(Produto param) {
        Produto p = Produto.acharPorNome(param.nome);
        if (p.id == null) {
            //param.copiarParaEntidade(p);
            CopiaHelper.copiar(p, param);
            p.persist();
        } else {
            ObjectId id = p.id;
            CopiaHelper.copiar(p, param);
            p.id = id;
            //param.adicionarNasListas(p);
            p.update();
        }
        //return new ProdutoComIdDto(p);
        return p;
    }

    public Produto alterar(String idStr, Produto param) {
        ObjectId id = new ObjectId(idStr);
        Optional<Produto> produto = Produto.findByIdOptional(id);
        produto.ifPresent(prod -> {
            ObjectId idX = prod.id;
            CopiaHelper.copiar(prod, param);
            prod.id = idX;
            prod.update();
        });
        return param;
    }
    public List<Produto> lerPorNome(String nome, int pagina, int tamanho) {
        return Produto.acharTodosPorNome(nome, pagina, tamanho);
        //return ProdutoComIdDto.paraListaObjetos(lista);
    }

    public Produto ler(String idStr) {
        ObjectId id = new ObjectId(idStr);
        return Produto.findById(id);
//        if (p == null) {
//            throw new ProdutoNaoEncontratoException();
//        }
//        return new ProdutoComIdDto(p);
    }

    public List<Produto> listar(int pagina, int tamanho) {
        return Produto.acharTodos(pagina, tamanho);
//        var res = ProdutoComIdDto.paraListaObjetos(lista);
//        log.debug(res.toString());
//        return res;
    }

    public List<ItemBuscaDto> listarTodosOsNomes() {
        return Produto.aTodos().stream()
                .map(ItemBuscaDto::new)
                .collect(Collectors.toList());
    }

    public void excluir(String idStr) {
        ObjectId id = new ObjectId(idStr);
        Optional<Produto> produto = Produto.findByIdOptional(id);
        produto.ifPresent(prod -> prod.delete());
    }

}
