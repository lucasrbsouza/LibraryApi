package io.github.lucasrbsouza.libraryapi.service;

import io.github.lucasrbsouza.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.repository.AutorRepository;
import io.github.lucasrbsouza.libraryapi.repository.LivroRepository;
import io.github.lucasrbsouza.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessario que o autor esteja na base de dados");
        }

        validator.validar(autor);

        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void delete(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Autor: "+ autor.getNome() +" - Não é permito deletar Autor que possui livros cadastrado");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            System.out.println("buscando por nome e nacionalidade");
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome != null) {
            System.out.println("buscando apenas por nome");
            return repository.findByNome(nome);
        } else if (nacionalidade != null) {
            System.out.println("buscando apenas por nacionalidade");
            return repository.findByNacionalidade(nacionalidade);
        } else {

            System.out.println("buscando todos");
            return repository.findAll();
        }
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}
