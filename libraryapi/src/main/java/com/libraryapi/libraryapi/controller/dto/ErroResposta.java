package com.libraryapi.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {

    public static ErroResposta erroPadrao (String mensagem) {
        ErroResposta erro = new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
        return erro;
    }

    public static ErroResposta conflito(String mensagem) {
        ErroResposta erro = new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
        return erro;
    }
}
