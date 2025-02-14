package io.github.lucasrbsouza.libraryapi.validator;

import io.github.lucasrbsouza.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasrbsouza.libraryapi.model.Autor;
import io.github.lucasrbsouza.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if (existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor Ja cadastrado");
        }
    }

    private Boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEcontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null){
            return  autorEcontrado.isPresent();
        }

        return !autor.getId().equals(autorEcontrado.get().getId()) && autorEcontrado.isPresent();
    }
}
