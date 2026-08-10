package com.raizes.raizesdonordeste.infrastructure.security;

import com.raizes.raizesdonordeste.domain.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetails implements UserDetails {
    private Usuario usuario;

    public UsuarioDetails(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        return this.usuario.getEmail();
    }

    @Override
    public String getPassword() {
        return this.usuario.getSenhaHash();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.usuario.getPerfil().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return List.of(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira neste exemplo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // Usuário ativo
    }
}
