package com.inavan.cadastro.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final HttpResponseStatus status;

    protected BaseException(HttpResponseStatus status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

}
