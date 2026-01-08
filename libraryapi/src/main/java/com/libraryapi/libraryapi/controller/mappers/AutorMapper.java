package com.libraryapi.libraryapi.controller.mappers;

import com.libraryapi.libraryapi.controller.dto.AutorDTO;
import com.libraryapi.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
