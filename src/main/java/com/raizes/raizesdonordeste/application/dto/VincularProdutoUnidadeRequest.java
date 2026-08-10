package com.raizes.raizesdonordeste.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record VincularProdutoUnidadeRequest(
        @NotNull
        UUID produtoId,

        @NotNull
        UUID unidadeId,

        @NotNull
        @Positive
        BigDecimal preco
) {
}