package com.raizes.raizesdonordeste.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponse(
        UUID id,
        String nome,
        String categoria,
        BigDecimal precoBase
) {}