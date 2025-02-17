package io.github.lucasrbsouza.libraryapi.controller.mappers;

import io.github.lucasrbsouza.libraryapi.controller.dtos.AutorDTO;
import io.github.lucasrbsouza.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper { //classe responsavel por mapear os objetos

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
