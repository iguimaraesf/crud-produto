package com.inavan.cadastro.dto;

import com.inavan.cadastro.entidade.produto.Produto;
import com.inavan.cadastro.helper.CopiaHelper;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
public class ProdutoComIdDto extends ProdutoDto {
    @EqualsAndHashCode.Include
    private String id;

    public ProdutoComIdDto(Produto entidade) {
        CopiaHelper.copiar(this, entidade);
        this.id = entidade.id.toHexString();
    }

    public static List<ProdutoComIdDto> paraListaObjetos(List<Produto> lista) {
        return lista.stream()
                .map(ProdutoComIdDto::new)
                .collect(Collectors.toList());
    }

}
