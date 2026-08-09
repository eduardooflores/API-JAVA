package com.raizes.raizesdonordeste.infrastructure.repository;

import com.raizes.raizesdonordeste.domain.enums.CanalPedido;
import com.raizes.raizesdonordeste.domain.enums.StatusPedido;
import com.raizes.raizesdonordeste.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
    List<Pedido> findByStatusPedido(StatusPedido statusPedido);
    List<Pedido> findByCanalPedidoAndStatusPedido(CanalPedido canalPedido, StatusPedido statusPedido);
}
