package com.libraryapi.libraryapi.repository;

import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.model.GeneroLivro;
import com.libraryapi.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void inserirAutor() {
        Autor autor = new Autor();

        autor.setNome("Bill Gates");
        autor.setNacionalidade("Estados Unidos");
        autor.setDataNascimento(LocalDate.of(1972, 2, 12));

        autorRepository.save(autor);
    }

    @Test
    void consultaPorId() {
        Autor autorEncontrado = autorRepository.findById(UUID.fromString("5a3486b4-6da6-4b37-aa30-a8c6e831c338")).orElse(null);

        System.out.println("Autor Encontrado: " + autorEncontrado);
    }

    @Test
    void atualizar() {
        Autor autorEncontrado = autorRepository.findById(UUID.fromString("5a3486b4-6da6-4b37-aa30-a8c6e831c338")).orElse(null);

        autorEncontrado.setDataNascimento(LocalDate.of(1955, 10, 28));

        autorRepository.save(autorEncontrado);

        System.out.println("Autor atualizado: " + autorEncontrado);
    }

    @Test
    void deletarPorId() {

        autorRepository.deleteById(UUID.fromString("8a297d11-5719-4da5-85c6-e174385586aa"));
    }

    @Test
    @Transactional
    void testeFetchLazy() {
        Autor autorEncontrado = autorRepository.findById(UUID.fromString("8c16605d-b1d7-4b75-9c22-805e5c55d2cc")).orElse(null);

        System.out.println("Autor encontrado: " + autorEncontrado);
        System.out.println("Livros: " + autorEncontrado.getLivros());
    }

    @Test
    void testeFetchEager() {
        Autor autorEncontrado = autorRepository.findById(UUID.fromString("8c16605d-b1d7-4b75-9c22-805e5c55d2cc")).orElse(null);

        System.out.println("Autor encontrado: " + autorEncontrado);
        System.out.println("Livros: " + autorEncontrado.getLivros());
    }

    @Test
    void testeCascade() {
        Autor autor = new Autor();

        autor.setNome("Machado de Assis");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1921, 10, 3));

        Livro livro1 = new Livro();
        livro1.setTitulo("Livro do Machado de Assis 1");
        livro1.setIsbn("3434gfdfggfsd");
        livro1.setDataPublicacao(LocalDate.of(1946, 4, 5));
        livro1.setGeneroLivro(GeneroLivro.ROMANCE);
        livro1.setPreco(BigDecimal.valueOf(89.99));
        livro1.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("Livro do Machado de Assis 2");
        livro2.setIsbn("4fdfds56ghfdgfd");
        livro2.setDataPublicacao(LocalDate.of(1947,2, 19));
        livro2.setGeneroLivro(GeneroLivro.ROMANCE);
        livro2.setPreco(BigDecimal.valueOf(59.99));
        livro2.setAutor(autor);

        List<Livro> livros = new ArrayList<>();
        livros.add(livro1);
        livros.add(livro2);

        autor.setLivros(livros);

        autorRepository.save(autor);
    }

    @Test
    void testeSemCascade() {
        Autor autor = new Autor();

        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950, 7, 12));

        autorRepository.save(autor);

        Livro livro1 = new Livro();
        livro1.setTitulo("Livro da Maria");
        livro1.setIsbn("123445r4tgffdsf");
        livro1.setDataPublicacao(LocalDate.of(1978, 11, 20));
        livro1.setGeneroLivro(GeneroLivro.FICCAO);
        livro1.setPreco(BigDecimal.valueOf(149.99));
        livro1.setAutor(autor);

        livroRepository.save(livro1);
    }
}
