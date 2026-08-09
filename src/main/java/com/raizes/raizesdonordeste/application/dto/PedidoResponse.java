package com.raizes.raizesdonordeste.application.dto;

import com.raizes.raizesdonordeste.domain.enums.CanalPedido;
import com.raizes.raizesdonordeste.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponse(
        UUID id,
        StatusPedido status,
        CanalPedido canalPedido,
        BigDecimal total,
        List<ItemPedidoResponse> itens,
        LocalDateTime createdAt
) {}
