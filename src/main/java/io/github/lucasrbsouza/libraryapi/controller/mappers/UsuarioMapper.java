package io.github.lucasrbsouza.libraryapi.controller.mappers;

import io.github.lucasrbsouza.libraryapi.controller.dtos.UsuarioDTO;
import io.github.lucasrbsouza.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario usuario);
}
