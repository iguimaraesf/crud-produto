package com.inavan.cadastro.exception;

public class ProdutoNaoEncontratoException extends AbstractNaoEncontratoException {
    public ProdutoNaoEncontratoException() {
        super("Produto não encontrado");
    }
}
