package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.CadastroUsuarioRequest;
import com.raizes.raizesdonordeste.application.dto.LoginRequest;
import com.raizes.raizesdonordeste.application.dto.LoginResponse;
import com.raizes.raizesdonordeste.application.dto.UsuarioResumidoResponse;
import com.raizes.raizesdonordeste.domain.enums.PerfilUsuario;
import com.raizes.raizesdonordeste.domain.model.Usuario;
import com.raizes.raizesdonordeste.infrastructure.repository.UsuarioRepository;
import com.raizes.raizesdonordeste.infrastructure.security.JwtService;
import com.raizes.raizesdonordeste.infrastructure.security.UsuarioDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse cadastrar(CadastroUsuarioRequest request) {

        Usuario usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario.setPerfil(PerfilUsuario.CLIENTE);

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario.getEmail());

        UsuarioResumidoResponse userResponse = new UsuarioResumidoResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getPerfil().name()
        );

        return new LoginResponse(
                token,
                "Bearer",
                (int) jwtService.getExpirationTime(),
                userResponse
        );
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.senha()
                )
        );

        Usuario usuario = ((UsuarioDetails) authentication.getPrincipal()).getUsuario();

        String token = jwtService.generateToken(usuario.getEmail());

        UsuarioResumidoResponse userResponse = new UsuarioResumidoResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getPerfil().name()
        );

        return new LoginResponse(
                token,
                "Bearer",
                (int) jwtService.getExpirationTime(),
                userResponse
        );
    }
}