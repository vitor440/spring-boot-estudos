package com.libraryapi.libraryapi.controller.mappers;

import com.libraryapi.libraryapi.controller.dto.AutorDTO;
import com.libraryapi.libraryapi.model.Autor;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T19:36:31-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (JetBrains s.r.o.)"
)
@Component
public class AutorMapperImpl implements AutorMapper {

    @Override
    public Autor toEntity(AutorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Autor autor = new Autor();

        autor.setId( dto.id() );
        autor.setNome( dto.nome() );
        autor.setNacionalidade( dto.nacionalidade() );
        autor.setDataNascimento( dto.dataNascimento() );

        return autor;
    }

    @Override
    public AutorDTO toDTO(Autor autor) {
        if ( autor == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String nacionalidade = null;
        LocalDate dataNascimento = null;

        id = autor.getId();
        nome = autor.getNome();
        nacionalidade = autor.getNacionalidade();
        dataNascimento = autor.getDataNascimento();

        AutorDTO autorDTO = new AutorDTO( id, nome, nacionalidade, dataNascimento );

        return autorDTO;
    }
}
