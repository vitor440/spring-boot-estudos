package com.libraryapi.libraryapi.controller.common;

import com.libraryapi.libraryapi.controller.dto.ErroCampo;
import com.libraryapi.libraryapi.controller.dto.ErroResposta;
import com.libraryapi.libraryapi.exceptions.CampoObrigatorioException;
import com.libraryapi.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.libraryapi.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
        return ErroResposta.erroPadrao(e.getMessage());
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(CampoObrigatorioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoObrigatorioException(CampoObrigatorioException e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErroGlobal(RuntimeException e) {
        ErroResposta erro = new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro no servidor. Entre em contato com a administração",
                List.of());

        return erro;
    }
}
