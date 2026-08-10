package com.raizes.raizesdonordeste.application.dto;

import com.raizes.raizesdonordeste.domain.enums.TipoMovimentacaoEstoque;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstoqueResponse(
        UUID id,
        UUID produtoId,
        UUID unidadeId,
        TipoMovimentacaoEstoque tipoMovimentacaoEstoque,
        int quantidade,
        LocalDateTime dataMovimentacao
) {}