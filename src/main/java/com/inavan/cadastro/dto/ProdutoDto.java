package com.inavan.cadastro.dto;

import com.inavan.cadastro.entidade.produto.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProdutoDto {
    private String nome;
    private Double pesoEmGramas;
    private Double volumeEmMililitros;
    private String imagem;
    private String descricao;
    private BigDecimal precoMinimo;
    private BigDecimal precoMaximo;
    private BigDecimal precoMedio;
    private BigDecimal precoMediano;
    private BigDecimal desvioPadraoPreco;
    private Set<String> nomesAlternativos = new HashSet<>();
    private Set<String> tags = new HashSet<>();
    private Set<InformacaoProdutoDto> informacoes = new HashSet<>();
    private Set<PrecoProdutoDto> precos = new HashSet<>();

    public void copiarParaEntidade(Produto entidade) {
        entidade.nome = this.nome;
        entidade.pesoEmGramas = this.pesoEmGramas;
        entidade.volumeEmMililitros = this.volumeEmMililitros;
        entidade.imagem = this.imagem;
        entidade.descricao = this.descricao;
        entidade.precoMinimo = this.precoMinimo;
        entidade.precoMaximo = this.precoMaximo;
        entidade.precoMedio = this.precoMedio;
        entidade.precoMediano = this.precoMediano;
        entidade.desvioPadraoPreco = this.desvioPadraoPreco;
        entidade.nomesAlternativos = Optional.ofNullable(this.nomesAlternativos).orElse(Collections.emptySet());
        entidade.tags = Optional.ofNullable(this.tags).orElse(Collections.emptySet());
        entidade.precos = PrecoProdutoDto.paraListaEntidade(this.precos);
        entidade.informacoes = InformacaoProdutoDto.paraListaEntidade(this.informacoes);
    }

    public void adicionarNasListas(Produto p) {
        p.nomesAlternativos.addAll(this.nomesAlternativos);
        p.tags.addAll(this.tags);
        p.informacoes.addAll(this.informacoes.stream().map(InformacaoProdutoDto::paraEntidade).collect(Collectors.toSet()));
        this.precos.forEach(precoDto -> {
            var entidadeTela = precoDto.paraEntidade();
            var entidadeBase = p.precos.stream().filter(pp -> Objects.equals(pp.getNomeComercio(), entidadeTela.getNomeComercio())).findAny().orElse(null);
            if (entidadeBase == null) {
                entidadeTela.setDataAtualizacao(LocalDateTime.now());
                p.precos.add(entidadeTela);
            } else if (!Objects.equals(entidadeTela.getPreco(), entidadeBase.getPreco())) {
                entidadeBase.setDataAtualizacao(LocalDateTime.now());
                entidadeBase.setPreco(entidadeTela.getPreco());
            }
        });
    }
}
