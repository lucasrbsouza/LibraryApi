package io.github.lucasrbsouza.libraryapi.controller.dtos;

import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroEnum genero,
        BigDecimal preco,
        AutorDTO autor
) {
}
