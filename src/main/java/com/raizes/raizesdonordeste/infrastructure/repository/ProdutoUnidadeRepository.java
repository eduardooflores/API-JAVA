package com.raizes.raizesdonordeste.infrastructure.repository;

import com.raizes.raizesdonordeste.domain.model.ProdutoUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProdutoUnidadeRepository extends JpaRepository<ProdutoUnidade, UUID> {
    List<ProdutoUnidade> findByUnidadeIdAndDisponivelTrue(UUID unidadeId);
}
