package io.github.lucasrbsouza.libraryapi.controller.dtos;

import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "campo obrigatorio")
        @ISBN
        String isbn,
        @NotBlank(message = "campo obrigatorio")
        String titulo,
        @NotNull
        @Past(message = "não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroEnum genero,
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID idAutor
) {
}
