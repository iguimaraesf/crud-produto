package com.inavan.cadastro.entidade.produto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class InformacaoProduto {
    private String nome;
    @EqualsAndHashCode.Exclude
    private String valor;
}
