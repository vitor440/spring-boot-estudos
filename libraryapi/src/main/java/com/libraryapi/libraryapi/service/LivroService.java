package com.libraryapi.libraryapi.service;

import com.libraryapi.libraryapi.model.GeneroLivro;
import com.libraryapi.libraryapi.model.Livro;
import com.libraryapi.libraryapi.repository.LivroRepository;
import com.libraryapi.libraryapi.repository.specs.SpecLivros;
import com.libraryapi.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {


    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public void salvar(Livro livro) {
        validator.validar(livro);
        livroRepository.save(livro);
    }

    public Page<Livro> pesquisa(String isbn, String titulo, Integer ano, String nomeAutor, GeneroLivro genero,
                                int numPaginas, int tamanhoPaginas) {
        Specification<Livro> specs = ((root, query, cb) -> cb.conjunction());

        if(isbn != null) {
            specs = specs.and(SpecLivros.equalIsbn(isbn));
        }

        if(titulo != null) {
            specs = specs.and(SpecLivros.likeTitulo(titulo));
        }

        if(genero != null) {
            specs = specs.and(SpecLivros.equalGenero(genero));
        }

        if(ano != null) {
            specs = specs.and(SpecLivros.equalAno(ano));
        }

        if(nomeAutor != null) {
            specs = specs.and(SpecLivros.likeNomeAutor(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(numPaginas, tamanhoPaginas);

        return livroRepository.findAll(specs, pageRequest);
    }

    public Optional<Livro> obterDetalhes(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null) {
            throw new IllegalArgumentException("Não é possivel atualizar livro que não esteja cadastrado na base de dados!");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }
}
