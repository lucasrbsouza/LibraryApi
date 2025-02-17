package io.github.lucasrbsouza.libraryapi.service;

import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.model.Usuario;
import io.github.lucasrbsouza.libraryapi.model.enums.GeneroEnum;
import io.github.lucasrbsouza.libraryapi.repository.LivroRepository;
import io.github.lucasrbsouza.libraryapi.security.SecurityService;
import io.github.lucasrbsouza.libraryapi.validator.LivroValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.lucasrbsouza.libraryapi.repository.spec.LivroSpecs.*;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public LivroService(LivroRepository livroRepository, LivroValidator validator, SecurityService securityService) {
        this.livroRepository = livroRepository;
        this.validator = validator;
        this.securityService = securityService;
    }


    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLOgado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterDetalhes(UUID id){
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn, String nomeAutor, GeneroEnum genero, Integer anoPublicacao, String titulo, Integer pagina, Integer tamanhoPagina){
        Specification<Livro> specs = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
         if (genero != null){
            specs = specs.and(generoEqual(genero));
        }
         if (anoPublicacao != null){
             specs = specs.and((anoPublicacaoEqual(anoPublicacao)));
         }
         if (nomeAutor != null && !nomeAutor.isBlank()){
             specs = specs.and(autorLike(nomeAutor));
         }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return livroRepository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("O livro ja tem que ser salvo na base para atualizar");
        }
        validator.validar(livro);
        livroRepository.save(livro);
    }
}
