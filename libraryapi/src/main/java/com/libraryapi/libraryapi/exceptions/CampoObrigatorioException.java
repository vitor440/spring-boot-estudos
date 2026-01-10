package com.libraryapi.libraryapi.exceptions;

import lombok.Getter;

public class CampoObrigatorioException extends RuntimeException {

    @Getter
    private String campo;

    public CampoObrigatorioException( String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
