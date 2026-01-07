package com.libraryapi.libraryapi.controller.common;

import com.libraryapi.libraryapi.controller.dto.ErroCampo;
import com.libraryapi.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExeptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErros = e.getFieldErrors();

        List<ErroCampo> erroCampos = fieldErros
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(),
                fieldError.getDefaultMessage())).collect(Collectors.toList());


        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação!",erroCampos);

    }
}
