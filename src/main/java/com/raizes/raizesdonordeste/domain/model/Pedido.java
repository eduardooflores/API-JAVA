package com.raizes.raizesdonordeste.domain.model;


import com.raizes.raizesdonordeste.domain.enums.CanalPedido;
import com.raizes.raizesdonordeste.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedido")
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CanalPedido canalPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido = StatusPedido.AGUARDANDO_PAGAMENTO;

    private BigDecimal total;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @PrePersist
    public void aoPersistir() {
        this.createdAt = LocalDateTime.now();
    }
}
