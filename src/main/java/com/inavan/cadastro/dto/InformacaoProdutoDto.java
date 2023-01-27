package com.inavan.cadastro.dto;

import com.inavan.cadastro.entidade.produto.InformacaoProduto;
import com.inavan.cadastro.entidade.produto.PrecoProduto;
import com.inavan.cadastro.helper.CopiaHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformacaoProdutoDto {
    private String nome;
    private String valor;

    public InformacaoProdutoDto(InformacaoProduto entidade) {
        CopiaHelper.copiar(this, entidade);
    }

    public InformacaoProduto paraEntidade() {
        InformacaoProduto entidade = new InformacaoProduto();
        CopiaHelper.copiar(entidade, this);
        return entidade;
    }

    public static Set<InformacaoProduto> paraListaEntidade(Set<InformacaoProdutoDto> informacoes) {
        return Optional.ofNullable(informacoes).orElse(Collections.emptySet())
                .stream()
                .map(InformacaoProdutoDto::paraEntidade)
                .collect(Collectors.toSet());
    }

}
