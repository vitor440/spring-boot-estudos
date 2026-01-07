package com.libraryapi.libraryapi.controller.dto;

import com.libraryapi.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
                       UUID id,
                       @NotBlank(message = "Campo Obrigatório!")
                       @Size(min = 2, max = 50, message = "Número de caracteres fora do padrão especificado!")
                       String nome,
                       @NotBlank(message = "Campo Obrigatório!")
                       @Size(min = 2, max = 30, message = "Número de caracteres fora do padrão especificado!")
                       String nacionalidade,
                       @NotNull(message = "Campo Obrigatório!")
                       LocalDate dataNascimento) {


    public Autor mapearAutor() {
        Autor autorEntidade = new Autor();

        autorEntidade.setNome(this.nome);
        autorEntidade.setNacionalidade(this.nacionalidade);
        autorEntidade.setDataNascimento(this.dataNascimento);
        return autorEntidade;
    }
}
