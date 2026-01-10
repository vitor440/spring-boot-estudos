package com.libraryapi.libraryapi.controller.mappers;

import com.libraryapi.libraryapi.controller.dto.CadastroLivroDTO;
import com.libraryapi.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.libraryapi.libraryapi.model.Livro;
import com.libraryapi.libraryapi.repository.AutorRepository;
import com.libraryapi.libraryapi.repository.LivroRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AutorMapper.class})
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(target = "autorDTO", source = "autor")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
