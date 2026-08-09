package com.raizes.raizesdonordeste.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    private String categoria;

    @Column(nullable = false)
    private BigDecimal precoBase;
}
