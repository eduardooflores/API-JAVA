package com.raizes.raizesdonordeste.application.dto;

import com.raizes.raizesdonordeste.domain.enums.CanalPedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CriarPedidoRequest(
        @NotNull CanalPedido canalPedido,
        @NotNull UUID unidadeId,
        @NotEmpty List<ItemPedidoRequest> itens
        ) {}
