package io.github.lucasrbsouza.libraryapi.repository;

import io.github.lucasrbsouza.libraryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRespository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}
