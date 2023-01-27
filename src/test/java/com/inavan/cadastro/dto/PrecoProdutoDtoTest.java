package com.inavan.cadastro.dto;

import com.inavan.cadastro.entidade.produto.PrecoProduto;
import com.inavan.cadastro.entidade.produto.Produto;
import com.inavan.cadastro.fixture.ProdutoFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrecoProdutoDtoTest {
    @Test
    void criarEntidadePreco() {
        var dto = ProdutoFixture.criarPrecoProduto();

        var res = dto.paraEntidade();
        Assertions.assertAll(() -> {
            Assertions.assertNotNull(res.getDataAtualizacao());
            Assertions.assertEquals(dto.getPreco(), res.getPreco());
            Assertions.assertEquals(dto.getNomeComercio(), res.getNomeComercio());
        });
    }

    @Test
    void criarEntidadeProdutoListasNulas() {
        var dto = ProdutoFixture.criarDto();
        dto.setNomesAlternativos(null);
        dto.setInformacoes(null);
        dto.setPrecos(null);
        dto.setTags(null);

        var res = new Produto();
        dto.copiarParaEntidade(res);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(dto.getNome(), res.nome);
            Assertions.assertEquals(dto.getImagem(), res.imagem);
            Assertions.assertEquals(dto.getDescricao(), res.descricao);
            Assertions.assertEquals(dto.getPesoEmGramas(), res.pesoEmGramas);
            Assertions.assertEquals(dto.getVolumeEmMililitros(), res.volumeEmMililitros);
        });
    }

    @Test
    void criarEntidadeProduto() {
        var dto = ProdutoFixture.criarDto();

        var res = new Produto();
        dto.copiarParaEntidade(res);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(dto.getNome(), res.nome);
            Assertions.assertEquals(dto.getImagem(), res.imagem);
            Assertions.assertEquals(dto.getDescricao(), res.descricao);
            Assertions.assertEquals(dto.getPesoEmGramas(), res.pesoEmGramas);
            Assertions.assertEquals(dto.getVolumeEmMililitros(), res.volumeEmMililitros);
            Assertions.assertTrue(res.precos.size() > 0);
            Assertions.assertTrue(res.informacoes.size() > 0);
            Assertions.assertTrue(res.nomesAlternativos.size() > 0);
            Assertions.assertTrue(res.tags.size() > 0);
            PrecoProduto primeiro = res.precos.stream().findAny().orElse(new PrecoProduto());
            Assertions.assertNotNull(primeiro.getDataAtualizacao());
        });
    }

    @Test
    void adicionarNaLista() {
        var dto = ProdutoFixture.criarDtoComEstabelecimentoNovo();
        var entidade = ProdutoFixture.criarEntidade();
        dto.adicionarNasListas(entidade);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(3, entidade.precos.size());
            PrecoProduto p = entidade.precos.stream()
                    .filter(prc -> prc.getNomeComercio().equals(ProdutoFixture.COMERCIO_IRMAOS))
                    .findAny().orElse(new PrecoProduto());

            Assertions.assertEquals(ProdutoFixture.PRECO_IRMAOS, p.getPreco());
        });
    }

    @Test
    void adicionarNaListaAtualizando() {
        var dto = ProdutoFixture.criarDtoComEstabelecimentoAtualizado();
        var entidade = ProdutoFixture.criarEntidade();
        dto.adicionarNasListas(entidade);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(2, entidade.precos.size());
            PrecoProduto p = entidade.precos.stream()
                    .filter(prc -> prc.getNomeComercio().equals(ProdutoFixture.COMERCIO_JOANA))
                    .findAny().orElse(new PrecoProduto());

            Assertions.assertEquals(ProdutoFixture.PRECO_JOANA_ATUALIZADO, p.getPreco());
        });
    }
}
