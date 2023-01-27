package com.inavan.cadastro.entidade.produto;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MongoEntity
public class Produto extends PanacheMongoEntity {
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

    public static Produto acharPorNome(String nome) {
        return aBuscaNome(nome).firstResultOptional().orElse(new Produto());
    }

    public static List<Produto> acharTodosPorNome(String nome, int pagina, int tamanho) {
        return aBuscaNome(nome).page(pagina, tamanho).stream().toList();
    }

    public static List<Produto> acharTodos(int pagina, int tamanho) {
        return aTodos().page(pagina, tamanho).stream().toList();
    }

    private static PanacheQuery<Produto> aBuscaNome(String nome) {
        return find("nomesAlternativos = ?1 OR nome = ?2", nome, nome);
    }

    public static PanacheQuery<Produto> aTodos() {
        return findAll();
    }
}
