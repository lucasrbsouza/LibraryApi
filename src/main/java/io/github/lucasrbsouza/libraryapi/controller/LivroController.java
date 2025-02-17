package io.github.lucasrbsouza.libraryapi.controller;

import io.github.lucasrbsouza.libraryapi.controller.dtos.CadastroLivroDTO;
import io.github.lucasrbsouza.libraryapi.controller.dtos.ResultadoPesquisaLivroDTO;
import io.github.lucasrbsouza.libraryapi.controller.mappers.LivroMapper;
import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import io.github.lucasrbsouza.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {
    private final LivroService livroService;
    private final LivroMapper mapper;

    public LivroController(LivroService livroService, LivroMapper mapper) {
        this.livroService = livroService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<?> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO) {
        Livro livro = mapper.toEntity(livroDTO);
        livroService.salvar(livro);

        URI location = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterDetalhes(UUID.fromString(id)).map(livro -> {
            ResultadoPesquisaLivroDTO dto = mapper.toDTO(livro);
            return ResponseEntity.ok().body(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        return livroService.obterDetalhes(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroEnum genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Livro> paginaResultado = livroService.pesquisa(isbn, nomeAutor, genero, anoPublicacao, titulo, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok().body(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<?> atualizar(@PathVariable("id") String id, @Valid @RequestBody CadastroLivroDTO dto) {
        return livroService.obterDetalhes(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setAutor(entidadeAux.getAutor());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
