package io.github.lucasrbsouza.libraryapi.controller.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(

        @NotBlank(message = "campo obrigatorio")
        String login,

        @NotBlank(message = "campo obrigatorio")
        String senha,

        @Email(message = "Email invalido")
        String email,

        List<String> roles) {
}
