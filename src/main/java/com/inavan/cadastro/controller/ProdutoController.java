package com.inavan.cadastro.controller;

import com.inavan.cadastro.dto.ItemBuscaDto;
import com.inavan.cadastro.dto.ProdutoComIdDto;
import com.inavan.cadastro.dto.ProdutoDto;
import com.inavan.cadastro.entidade.produto.Produto;
import com.inavan.cadastro.service.ProdutoService;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import java.util.List;

@Path("/api/v1/produto")
public class ProdutoController {
    ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GET
    public Uni<List<Produto>> listar(@QueryParam("page") @DefaultValue("0") int pagina, @QueryParam("size") @DefaultValue("20") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @POST
    public Uni<?> incluirOuAtualizar(Produto dto) {
        return service.incluirOuAlterar(dto);
    }

    @GET
    @Path("/{id}")
    public Uni<Produto> ler(@PathParam("id") String id) {
        return service.ler(id);
    }

    @PUT
    @Path("/{id}")
    public Uni<?> alterarPorId(@PathParam("id") String id, Produto dto) {
        return service.alterar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void excluir(@PathParam("id") String id) {
        service.excluir(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Uni<List<Produto>> listarPorNome(@PathParam("nome") String nome, @QueryParam("page") int pagina, @QueryParam("size") int tamanho) {
        return service.lerPorNome(nome, pagina, tamanho);
    }

    @GET
    @Path("/so-nomes")
    public Uni<List<ItemBuscaDto>> listarTodosOsNomes() {
        return service.listarTodosOsNomes();
    }
}
