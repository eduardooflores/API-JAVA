package com.raizes.raizesdonordeste.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoUnidadeResponse(
        UUID id,
        UUID produtoId,
        UUID unidadeId,
        BigDecimal preco,
        boolean disponivel
) {
}