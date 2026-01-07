package com.libraryapi.libraryapi.repository;

import com.libraryapi.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    public List<Autor> findByNome(String nome);

    public List<Autor> findByNacionalidade(String nacionalidade);

    public List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    public Optional<Autor> findByNomeAndNacionalidadeAndDataNascimento(String nome, String nacionalidade, LocalDate dataNascimento);
}
