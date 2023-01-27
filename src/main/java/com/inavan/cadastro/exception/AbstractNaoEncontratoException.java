package com.inavan.cadastro.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class AbstractNaoEncontratoException extends BaseException {

    protected AbstractNaoEncontratoException(String mensagem) {
        super(HttpResponseStatus.PRECONDITION_FAILED, mensagem);
    }

}
