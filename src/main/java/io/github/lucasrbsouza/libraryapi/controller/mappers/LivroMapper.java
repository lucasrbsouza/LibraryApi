package io.github.lucasrbsouza.libraryapi.controller.mappers;

import io.github.lucasrbsouza.libraryapi.controller.dtos.CadastroLivroDTO;
import io.github.lucasrbsouza.libraryapi.controller.dtos.ResultadoPesquisaLivroDTO;
import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(cadastroLivroDTO.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO cadastroLivroDTO);

    @Mapping(target = "autor", source = "autor")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
