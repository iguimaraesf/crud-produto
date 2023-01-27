package com.inavan.cadastro.entidade.produto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@Setter
public class PrecoProduto {
    private String nomeComercio;
    private BigDecimal preco;
    private LocalDateTime dataAtualizacao;
}
