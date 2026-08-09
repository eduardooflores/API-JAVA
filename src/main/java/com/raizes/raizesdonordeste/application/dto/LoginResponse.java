package com.raizes.raizesdonordeste.application.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        int expiresIn,
        UsuarioResumidoResponse user
) {}
