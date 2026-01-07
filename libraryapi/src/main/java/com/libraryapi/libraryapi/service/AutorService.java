package com.libraryapi.libraryapi.service;

import com.libraryapi.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.repository.AutorRepository;
import com.libraryapi.libraryapi.repository.LivroRepository;
import com.libraryapi.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator validator;

    public Autor salvar(Autor autor) {

        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir autor com livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> buscaFiltrada(String nome, String nacionalidade) {

        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null) {
            return autorRepository.findByNome(nome);
        }

        if(nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                                .matching()
                                .withIgnoreCase()
                                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);


        Example<Autor> exampleAutor = Example.of(autor, matcher);

        return autorRepository.findAll(exampleAutor);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base!");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
