package com.inavan.cadastro.dto;

import com.inavan.cadastro.entidade.produto.PrecoProduto;
import com.inavan.cadastro.helper.CopiaHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PrecoProdutoDto {
    private String nomeComercio;
    private BigDecimal preco;
    private LocalDateTime data;

    public PrecoProdutoDto(PrecoProduto entidade) {
        CopiaHelper.copiar(this, entidade);
        this.data = entidade.getDataAtualizacao();
    }

    public PrecoProduto paraEntidade() {
        PrecoProduto entidade = new PrecoProduto();
        CopiaHelper.copiar(entidade, this);
        entidade.setDataAtualizacao(Optional.ofNullable(this.data).orElse(LocalDateTime.now()));
        return entidade;
    }

    public static Set<PrecoProduto> paraListaEntidade(Set<PrecoProdutoDto> precos) {
        return Optional.ofNullable(precos).orElse(Collections.emptySet())
                .stream()
                .map(PrecoProdutoDto::paraEntidade)
                .collect(Collectors.toSet());
    }

}
