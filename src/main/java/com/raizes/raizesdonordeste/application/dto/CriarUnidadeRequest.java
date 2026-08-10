package com.raizes.raizesdonordeste.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarUnidadeRequest(
        @NotBlank String nome,
        @NotBlank String endereco
) {}