package io.github.lucasrbsouza.libraryapi.security;

import io.github.lucasrbsouza.libraryapi.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomAuthentication implements Authentication {

    private final Usuario usuario;

    public CustomAuthentication(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.usuario.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return usuario.getLogin();
    }
}
