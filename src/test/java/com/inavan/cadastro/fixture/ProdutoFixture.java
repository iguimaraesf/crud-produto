package com.inavan.cadastro.fixture;

import com.inavan.cadastro.dto.InformacaoProdutoDto;
import com.inavan.cadastro.dto.PrecoProdutoDto;
import com.inavan.cadastro.dto.ProdutoComIdDto;
import com.inavan.cadastro.entidade.produto.PrecoProduto;
import com.inavan.cadastro.entidade.produto.Produto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProdutoFixture {

    public static final String COMERCIO_JOANA = "Casa da Mãe Joana Ltda";
    public static final BigDecimal PRECO_JOANA = new BigDecimal("22.45");
    public static final BigDecimal PRECO_JOANA_ATUALIZADO = new BigDecimal("21.99");
    public static final String COMERCIO_ZE = "Mercadinho do Seu Zé";
    public static final BigDecimal PRECO_ZE = new BigDecimal("22.02");
    public static final String COMERCIO_IRMAOS = "Depósito Dois Irmãos";
    public static final BigDecimal PRECO_IRMAOS = new BigDecimal("22.59");

    public static ProdutoComIdDto criarDtoComEstabelecimentoNovo() {
        var dto = new ProdutoComIdDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setPrecos(new HashSet<>(Set.of(criarPrecoProduto())));
        return dto;
    }

    public static PrecoProdutoDto criarPrecoProduto() {
        return dtoPrecoProduto(PRECO_IRMAOS, COMERCIO_IRMAOS);
    }

    public static ProdutoComIdDto criarDtoComEstabelecimentoAtualizado() {
        var dto = new ProdutoComIdDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setPrecos(new HashSet<>(Set.of(dtoPrecoProduto(PRECO_JOANA_ATUALIZADO, COMERCIO_JOANA))));
        return dto;
    }

    public static Produto criarEntidade() {
        var ent = new Produto();
        ent.precos = new HashSet<>(Set.of(
                entidadePrecoProduto(PRECO_JOANA, COMERCIO_JOANA),
                entidadePrecoProduto(PRECO_ZE, COMERCIO_ZE)));
        ent.id = new ObjectId();
        ent.nome = "Teste";
        ent.descricao = "Descrição";
        ent.pesoEmGramas = 130.0;
        ent.volumeEmMililitros = 0.44;
        ent.imagem = "https://imagem.site.com/p.png";
        return ent;
    }

    public static ProdutoComIdDto criarDto() {
        var ent = new ProdutoComIdDto();
        ent.setId(UUID.randomUUID().toString());
        ent.setNome("Teste dto");
        ent.setDescricao("desc dto");
        ent.setPesoEmGramas(135.0);
        ent.setVolumeEmMililitros(0.45);
        ent.setImagem("http://imagem.site.com/p.png");
        ent.setPrecos(new HashSet<>(Set.of(
                dtoPrecoProduto(PRECO_ZE, COMERCIO_ZE)
        )));
        ent.setNomesAlternativos(Set.of("Um teste dto"));
        ent.setTags(Set.of("Teste"));
        ent.setInformacoes(Set.of(new InformacaoProdutoDto("Peso", "135g")));
        return ent;
    }
    private static PrecoProduto entidadePrecoProduto(BigDecimal valor, String comercio) {
        var preco = new PrecoProduto();
        preco.setPreco(valor);
        preco.setDataAtualizacao(LocalDateTime.now());
        preco.setNomeComercio(comercio);
        return preco;
    }

    private static PrecoProdutoDto dtoPrecoProduto(BigDecimal valor, String comercio) {
        var dto = new PrecoProdutoDto();
        dto.setPreco(valor);
        // dto.setData(LocalDateTime.now());
        dto.setNomeComercio(comercio);
        return dto;
    }

}
