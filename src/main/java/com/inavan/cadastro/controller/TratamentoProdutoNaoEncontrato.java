package com.inavan.cadastro.controller;

import com.inavan.cadastro.exception.ProdutoNaoEncontratoException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TratamentoProdutoNaoEncontrato implements ExceptionMapper<ProdutoNaoEncontratoException> {

    @Override
    public Response toResponse(ProdutoNaoEncontratoException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new MensagemErro(e)).build();
    }
}
