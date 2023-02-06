package com.inavan.cadastro.controller;

import com.inavan.cadastro.exception.BaseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MensagemErro {
    private final String mensagem;
    private final LocalDateTime dataHora;

    public MensagemErro(BaseException e) {
        this.mensagem = e.getMessage();
        this.dataHora = LocalDateTime.now();
    }
}
