package io.github.lucasrbsouza.libraryapi.repository;

import io.github.lucasrbsouza.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
