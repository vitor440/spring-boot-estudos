package com.libraryapi.libraryapi.repository;

import com.libraryapi.libraryapi.model.Autor;
import com.libraryapi.libraryapi.model.GeneroLivro;
import com.libraryapi.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void inserir() {
        Autor autorEncontrado = autorRepository.findById(UUID.fromString("bc98d493-d15f-45a4-a0cc-0afc3cb6aaeb")).orElse(null);


        Livro livro = new Livro();

        livro.setIsbn("1234njdf94d");
        livro.setTitulo("Spider-Man");
        livro.setGeneroLivro(GeneroLivro.FICCAO);
        livro.setDataPublicacao(LocalDate.of(1982, 11, 9));
        livro.setPreco(BigDecimal.valueOf(159.99));

        livro.setAutor(autorEncontrado);

        livroRepository.save(livro);

    }

    @Test
    void consultaPorId() {
        Livro livroEncontrado = livroRepository.findById(UUID.fromString("9912c53b-d0a9-462b-bcaa-e9c2a68ac6f5")).orElse(null);

        System.out.println("Livro Encontrado: " + livroEncontrado);
    }

    @Test
    void deletarPorId() {
        livroRepository.deleteById(UUID.fromString("00c2f8fa-ce4a-48b3-933d-22f5f095d161"));
    }

    @Test
    void atualizarLivro() {
        Livro livroEncontrado = livroRepository.findById(UUID.fromString("9912c53b-d0a9-462b-bcaa-e9c2a68ac6f5")).orElse(null);

        livroEncontrado.setTitulo("Livro do José");

        livroRepository.save(livroEncontrado);

    }

    @Test
    void TesteFetchEager() {
        Livro livroEncontrado = livroRepository.findById(UUID.fromString("7682b8fd-e617-427f-a68c-fe0382cfa225")).orElse(null);

        System.out.println("Livro Encontrado: " + livroEncontrado);

        System.out.println("Autor do Livro: " + livroEncontrado.getAutor());
    }

    @Test
    @Transactional
    void TesteFetchLazy() {
        Livro livroEncontrado = livroRepository.findById(UUID.fromString("7682b8fd-e617-427f-a68c-fe0382cfa225")).orElse(null);

        System.out.println("Livro Encontrado: " + livroEncontrado);

        System.out.println("Autor do Livro: " + livroEncontrado.getAutor());
    }

    @Test
    void testeCascade() {

        Livro livro = new Livro();

        livro.setTitulo("Spider-man");
        livro.setIsbn("904545r21344334");
        livro.setGeneroLivro(GeneroLivro.FANTASIA);
        livro.setDataPublicacao(LocalDate.of(1987, 8, 4));
        livro.setPreco(BigDecimal.valueOf(78.00));

        Autor autor = new Autor();

        autor.setNome("Stan Lee");
        autor.setNacionalidade("Estados Unidos");
        autor.setDataNascimento(LocalDate.of(1943, 6, 12));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void testeSemCascade() {
        Autor autor = new Autor();

        autor.setNome("William");
        autor.setNacionalidade("Estados Unidos");
        autor.setDataNascimento(LocalDate.of(1963, 10, 12));

        autorRepository.save(autor);

        Livro livro = new Livro();

        livro.setTitulo("Spider-man");
        livro.setIsbn("904545r21344334");
        livro.setGeneroLivro(GeneroLivro.FANTASIA);
        livro.setDataPublicacao(LocalDate.of(1987, 8, 4));
        livro.setPreco(BigDecimal.valueOf(78.00));
        livro.setAutor(autor);

        livroRepository.save(livro);
    }
}
