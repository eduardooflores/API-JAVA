package com.raizes.raizesdonordeste.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponse(
        UUID produtoId,
        int quantidade,
        BigDecimal precoUnitario
) {}
