package io.github.lucasrbsouza.libraryapi.validator;

import io.github.lucasrbsouza.libraryapi.exceptions.CampoInvalidoException;
import io.github.lucasrbsouza.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasrbsouza.libraryapi.model.Livro;
import io.github.lucasrbsouza.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;
    private final LivroRepository repository;

    public LivroValidator(LivroRepository repository) {
        this.repository = repository;
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return  livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    public void validar(Livro livro){
        if (existsLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já Cadastrado");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para Livros com ano de publicacao a partir de 2020 o preco é obrigatorio");
        }
    }

    private boolean existsLivroComIsbn(Livro livro){
        Optional<Livro> livroEnontrado = repository.findByIsbn(livro.getIsbn());
        if (livro.getId()==null){
            return livroEnontrado.isPresent();
        }

        return livroEnontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));

    }
}
