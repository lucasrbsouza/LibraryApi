package io.github.lucasrbsouza.libraryapi.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,

        @NotBlank(message = "Campo Obrigatório")//Not Blank é para String não vim nula e nem vazia
        @Size(min = 2, max = 100, message = "Campo Fora Do tamanho")
        String nome,
        @NotNull(message = "Campo Obrigatório") //Not Null Campos que pode vim nulo
        @Past(message = "não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo Obrigatório")
        @Size(min = 2, max = 50, message = "Campo Fora Do tamanho")
        String nacionalidade) {

}
