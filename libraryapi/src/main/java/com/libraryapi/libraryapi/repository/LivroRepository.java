package com.libraryapi.libraryapi.repository;

import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor {

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);
}
