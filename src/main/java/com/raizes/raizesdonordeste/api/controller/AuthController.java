package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.CadastroUsuarioRequest;
import com.raizes.raizesdonordeste.application.dto.LoginRequest;
import com.raizes.raizesdonordeste.application.dto.LoginResponse;
import com.raizes.raizesdonordeste.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<LoginResponse> cadastrar(
            @Valid @RequestBody CadastroUsuarioRequest request
    ) {
        LoginResponse response = authService.cadastrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}
