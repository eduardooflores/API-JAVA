package com.raizes.raizesdonordeste.infrastructure.security;

import com.raizes.raizesdonordeste.infrastructure.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UsuarioDetails(usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email não encontrado.")));
    }
}
