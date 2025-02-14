package io.github.lucasrbsouza.libraryapi.controller;

import io.github.lucasrbsouza.libraryapi.controller.dtos.AutorDTO;
import io.github.lucasrbsouza.libraryapi.controller.dtos.ErroResposta;
import io.github.lucasrbsouza.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasrbsouza.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

    public final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autorDTO) { //@RequestBody Indica que o objeto vair ir no body
        try {


            var autorEntidade = autorDTO.mapearParaAutor();
            autorService.salvar(autorEntidade);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }catch (RegistroDuplicadoException ex){
            var erroDTO = ErroResposta.conflito(ex.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO); //passamos o status que sera dado caso de erro, e depois colocamos no body o texto de erro
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhase(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.obterPorId(idAutor);
        if (autor.isPresent()) {
            Autor entidade = autor.get();
            AutorDTO autorDTO = new AutorDTO(entidade.getId(), entidade.getNome(), entidade.getDataNascimento(), entidade.getNacionalidade());

            return ResponseEntity.ok(autorDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autor = autorService.obterPorId(idAutor);

            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            autorService.delete(autor.get());

            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroDto = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        //@RequestParam é a anotacao para Query Params, e o required é pra dizer se é obrigatorio ou não, pr default ele é obrigatório

        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> pesquisa = resultado.stream()
                .map(
                        autor ->
                                new AutorDTO(
                                        autor.getId(),
                                        autor.getNome(),
                                        autor.getDataNascimento(),
                                        autor.getNacionalidade()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(pesquisa);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto) {
        try {
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
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}
