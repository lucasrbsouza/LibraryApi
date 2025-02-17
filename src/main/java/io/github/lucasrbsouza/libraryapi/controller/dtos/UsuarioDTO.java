package io.github.lucasrbsouza.libraryapi.controller.dtos;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
