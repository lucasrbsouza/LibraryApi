package io.github.lucasrbsouza.libraryapi.security;

import io.github.lucasrbsouza.libraryapi.model.Usuario;
import io.github.lucasrbsouza.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UsuarioService usuarioService;
    public Usuario obterUsuarioLOgado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuth){
            return customAuth.getUsuario();
        }
        return null;
    }
}
