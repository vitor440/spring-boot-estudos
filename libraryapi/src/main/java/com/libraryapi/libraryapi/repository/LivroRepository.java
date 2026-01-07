package com.libraryapi.libraryapi.repository;

import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    boolean existsByAutor(Autor autor);
}
