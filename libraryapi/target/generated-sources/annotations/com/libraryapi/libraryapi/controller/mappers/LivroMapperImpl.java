package com.libraryapi.libraryapi.controller.mappers;

import com.libraryapi.libraryapi.controller.dto.AutorDTO;
import com.libraryapi.libraryapi.controller.dto.CadastroLivroDTO;
import com.libraryapi.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.libraryapi.libraryapi.model.GeneroLivro;
import com.libraryapi.libraryapi.model.Livro;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-12T22:25:03-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (JetBrains s.r.o.)"
)
@Component
public class LivroMapperImpl extends LivroMapper {

    @Autowired
    private AutorMapper autorMapper;

    @Override
    public Livro toEntity(CadastroLivroDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Livro livro = new Livro();

        livro.setIsbn( dto.isbn() );
        livro.setTitulo( dto.titulo() );
        livro.setDataPublicacao( dto.dataPublicacao() );
        livro.setGeneroLivro( dto.generoLivro() );
        livro.setPreco( dto.preco() );

        livro.setAutor( autorRepository.findById(dto.idAutor()).orElse(null) );

        return livro;
    }

    @Override
    public ResultadoPesquisaLivroDTO toDTO(Livro livro) {
        if ( livro == null ) {
            return null;
        }

        AutorDTO autorDTO = null;
        UUID id = null;
        String isbn = null;
        String titulo = null;
        LocalDate dataPublicacao = null;
        GeneroLivro generoLivro = null;
        BigDecimal preco = null;

        autorDTO = autorMapper.toDTO( livro.getAutor() );
        id = livro.getId();
        isbn = livro.getIsbn();
        titulo = livro.getTitulo();
        dataPublicacao = livro.getDataPublicacao();
        generoLivro = livro.getGeneroLivro();
        preco = livro.getPreco();

        ResultadoPesquisaLivroDTO resultadoPesquisaLivroDTO = new ResultadoPesquisaLivroDTO( id, isbn, titulo, dataPublicacao, generoLivro, preco, autorDTO );

        return resultadoPesquisaLivroDTO;
    }
}
