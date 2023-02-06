package com.inavan.cadastro.entidade.produto;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheQuery;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MongoEntity
@ToString
public class Produto extends ReactivePanacheMongoEntity {
    public ObjectId id;
    public String nome;
    public Double pesoEmGramas;
    public Double volumeEmMililitros;
    public String imagem;
    public String descricao;
    public BigDecimal precoMinimo;
    public BigDecimal precoMaximo;
    public BigDecimal precoMedio;
    public BigDecimal precoMediano;
    public BigDecimal desvioPadraoPreco;
    public Set<String> nomesAlternativos = new HashSet<>();
    public Set<String> tags = new HashSet<>();
    public Set<InformacaoProduto> informacoes = new HashSet<>();
    public Set<PrecoProduto> precos = new HashSet<>();

    public static Uni<Produto> acharPorNome(String nome) {
        return aBuscaNome(nome).firstResult();
    }

    public static Uni<List<Produto>> acharTodosPorNome(String nome, int pagina, int tamanho) {
        var q = aBuscaNome(nome).page(pagina, tamanho);
        return q.list();
    }

    public static Uni<List<Produto>> acharTodos(int pagina, int tamanho) {
        var q = aTodos().page(pagina, tamanho);
        return q.list();
    }

    private static ReactivePanacheQuery<Produto> aBuscaNome(String nome) {
        return find("nomesAlternativos = ?1 OR nome = ?2", nome, nome);
    }

    public static ReactivePanacheQuery<Produto> aTodos() {
        return findAll();
    }
}
