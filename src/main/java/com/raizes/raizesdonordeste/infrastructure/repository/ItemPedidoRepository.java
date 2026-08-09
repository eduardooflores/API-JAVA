package com.raizes.raizesdonordeste.infrastructure.repository;

import com.raizes.raizesdonordeste.domain.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {
    List<ItemPedido> findByPedidoId(UUID pedidoId);
}
