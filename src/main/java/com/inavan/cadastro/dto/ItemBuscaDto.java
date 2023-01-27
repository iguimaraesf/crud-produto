package com.inavan.cadastro.dto;

import com.inavan.cadastro.entidade.produto.Produto;
import lombok.Data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Data
public class ItemBuscaDto {
    private List<String> nomesPossiveis;
    private List<String> tags;

    public ItemBuscaDto(Produto entidade) {
        if (entidade == null) {
            return;
        }
        nomesPossiveis = new LinkedList<>();
        nomesPossiveis.add(entidade.nome);
        nomesPossiveis.addAll(entidade.nomesAlternativos);

        tags = Optional.ofNullable(entidade.tags).orElse(new HashSet<>()).stream().toList();
    }
}
