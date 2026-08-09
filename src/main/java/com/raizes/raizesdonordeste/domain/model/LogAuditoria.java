package com.raizes.raizesdonordeste.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_auditoria")
@Getter
@Setter
public class LogAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String acao;

    private String entidadeAfetada;

    private UUID entidadeId;

    private LocalDateTime dataHora;

    @PrePersist
    public void aoPersistir() {
        this.dataHora = LocalDateTime.now();
    }
}
