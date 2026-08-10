package com.raizes.raizesdonordeste.application.dto;

import java.util.UUID;

public record UnidadeResponse(
        UUID id,
        String nome,
        String endereco,
        boolean ativa
) {}