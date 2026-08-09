package com.raizes.raizesdonordeste.domain.model;


import com.raizes.raizesdonordeste.domain.enums.TipoMovimentacaoEstoque;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tipo_movimentacao")
@Getter
@Setter
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoEstoque tipoMovimentacaoEstoque;

    @Column(nullable = false)
    private int quantidade;

    private LocalDateTime dataMovimentacao;

    @PrePersist
    public void aoPersistir() {
        this.dataMovimentacao = LocalDateTime.now();
    }
}
