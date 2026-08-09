package com.raizes.raizesdonordeste.infrastructure.repository;

import com.raizes.raizesdonordeste.domain.model.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID> {
    List<LogAuditoria> findByUsuarioId(UUID usuarioId);
    List<LogAuditoria> findByEntidadeId(UUID entidadeId);
}
