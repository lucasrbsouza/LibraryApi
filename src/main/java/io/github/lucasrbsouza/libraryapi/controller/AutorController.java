package io.github.lucasrbsouza.libraryapi.controller;

import io.github.lucasrbsouza.libraryapi.controller.dtos.AutorDTO;
import io.github.lucasrbsouza.libraryapi.controller.mappers.AutorMapper;
import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.model.Usuario;
import io.github.lucasrbsouza.libraryapi.security.SecurityService;
import io.github.lucasrbsouza.libraryapi.service.AutorService;
import io.github.lucasrbsouza.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController implements GenericController {

    public final AutorService autorService;
    private final AutorMapper autorMapper;
    private final SecurityService securityService;

    public AutorController(AutorService autorService, AutorMapper autorMapper, SecurityService securityService) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
        this.securityService = securityService;
    }

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> salvar(@Valid @RequestBody AutorDTO autorDTO) { //@RequestBody Indica que o objeto vair ir no body

        Autor autorEntidade = autorMapper.toEntity(autorDTO);
        autorService.salvar(autorEntidade);

        URI location = gerarHeaderLocation(autorEntidade.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhase(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);

        return autorService.obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterPorId(idAutor);

        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.delete(autor.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        //@RequestParam é a anotacao para Query Params, e o required é pra dizer se é obrigatorio ou não, pr default ele é obrigatório

        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> pesquisa = resultado.stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pesquisa);

    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @Valid @RequestBody AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterPorId(idAutor);

        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autorGet = autor.get();

        autorGet.setNome(dto.nome());
        autorGet.setNacionalidade(dto.nacionalidade());
        autorGet.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autorGet);

        return ResponseEntity.noContent().build();

    }
}
