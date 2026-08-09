package com.raizes.raizesdonordeste.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "item_pedido")
@Getter
@Setter
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    private BigDecimal precoUnitario;
}
