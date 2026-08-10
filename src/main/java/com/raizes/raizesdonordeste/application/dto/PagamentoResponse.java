package com.raizes.raizesdonordeste.application.dto;

import com.raizes.raizesdonordeste.domain.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoResponse(
        UUID id,
        UUID pedidoId,
        StatusPagamento status,
        BigDecimal valorPago,
        String payload,
        LocalDateTime dataPagamento
) {}
