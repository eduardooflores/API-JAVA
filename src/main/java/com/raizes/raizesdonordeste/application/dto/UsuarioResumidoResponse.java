package com.raizes.raizesdonordeste.application.dto;

import java.util.UUID;

public record UsuarioResumidoResponse(
        UUID id,
        String nome,
        String perfil
) {}
