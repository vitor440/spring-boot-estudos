package com.libraryapi.libraryapi.validator;

import com.libraryapi.libraryapi.exceptions.RegistroDuplicadoException;
import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository
                .findByNomeAndNacionalidadeAndDataNascimento(autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento());

        if(autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
