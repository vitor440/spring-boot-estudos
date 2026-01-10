package com.libraryapi.libraryapi.validator;

import com.libraryapi.libraryapi.exceptions.CampoObrigatorioException;
import com.libraryapi.libraryapi.exceptions.RegistroDuplicadoException;
import com.libraryapi.libraryapi.model.Livro;
import com.libraryapi.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LivroValidator {

    private final LivroRepository repository;
    private static final int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro) {

        if(existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("Não é permitido cadastrar livro com isbn repetido!");
        }

        if(isPrecoObrigatorioNulo(livro)) {
            throw new CampoObrigatorioException("preco", "Para livros com ano de publicação a partir de 2020, o campo preço é obrigatório!");
        }
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null) {
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
        }
    }

