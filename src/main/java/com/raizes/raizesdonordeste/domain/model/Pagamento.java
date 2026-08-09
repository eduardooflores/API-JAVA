package com.raizes.raizesdonordeste.domain.model;


import com.raizes.raizesdonordeste.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    private BigDecimal valorPago;

    private LocalDateTime dataPagamento;

    private String payload;

    @PrePersist
    public void aoPersistir() {
        this.dataPagamento = LocalDateTime.now();
    }

}
