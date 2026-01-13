package com.libraryapi.libraryapi.repository.specs;

import com.libraryapi.libraryapi.model.GeneroLivro;
import com.libraryapi.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class SpecLivros {

    public static Specification<Livro> equalIsbn(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> likeTitulo(String titulo) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> equalGenero(GeneroLivro generoLivro) {
        return (root, query, cb) -> cb.equal(root.get("generoLivro"), generoLivro);
    }

    public static Specification<Livro> equalAno(Integer ano) {
        return (root, query, cb) -> cb
                .equal(cb
                .function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), ano);
    }

    public static Specification<Livro> likeNomeAutor(String nomeAutor) {

        return (root, query, cb) -> {
            Join<Object, Object> autor = root.join("autor", JoinType.LEFT);

            return cb.like(cb.upper(autor.get("nome")), nomeAutor.toUpperCase());
        };
    }
}
