package com.raizes.raizesdonordeste.infrastructure.repository;

import com.raizes.raizesdonordeste.domain.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {
    List<Estoque> findByProdutoIdAndUnidadeId(UUID produtoId, UUID unidadeId);
}
