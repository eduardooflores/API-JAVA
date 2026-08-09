package com.raizes.raizesdonordeste.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produto_unidade")
@Getter
@Setter
public class ProdutoUnidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    private boolean disponivel = true;

    @Column(nullable = false)
    private BigDecimal preco;
}
