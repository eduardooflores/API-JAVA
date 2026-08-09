package com.raizes.raizesdonordeste.infrastructure.repository;

import com.raizes.raizesdonordeste.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    List<Pagamento> findByPedidoId(UUID pedidoId);
}
