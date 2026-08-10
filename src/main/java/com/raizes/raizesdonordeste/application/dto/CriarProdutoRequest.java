package com.raizes.raizesdonordeste.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CriarProdutoRequest(
        @NotBlank String nome,
        String categoria,
        @NotNull @Positive BigDecimal precoBase
) {}