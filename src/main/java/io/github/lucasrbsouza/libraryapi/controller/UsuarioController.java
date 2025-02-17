package io.github.lucasrbsouza.libraryapi.controller;

import io.github.lucasrbsouza.libraryapi.controller.dtos.UsuarioDTO;
import io.github.lucasrbsouza.libraryapi.controller.mappers.UsuarioMapper;
import io.github.lucasrbsouza.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;
    private final UsuarioMapper mapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@Valid @RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
