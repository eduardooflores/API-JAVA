package com.raizes.raizesdonordeste.domain.model;

import com.raizes.raizesdonordeste.domain.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    private String senhaHash;

    @PrePersist
    public void aoPersistir() {
        this.dataCadastro = LocalDateTime.now();
    }
}
