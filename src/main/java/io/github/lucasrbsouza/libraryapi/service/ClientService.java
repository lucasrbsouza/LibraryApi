package io.github.lucasrbsouza.libraryapi.service;

import io.github.lucasrbsouza.libraryapi.model.Client;
import io.github.lucasrbsouza.libraryapi.repository.ClientRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRespository respository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        var senhaCript = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCript);
        return respository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return respository.findByClientId(clientId);
    }
}
