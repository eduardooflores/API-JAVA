package com.raizes.raizesdonordeste.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ItemPedidoRequest(
        @NotNull UUID produtoId,
        @Positive int quantidade
) {}
