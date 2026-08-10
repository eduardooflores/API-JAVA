package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.EstoqueResponse;
import com.raizes.raizesdonordeste.application.dto.RegistrarEstoqueRequest;
import com.raizes.raizesdonordeste.application.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("/entrada")
    public ResponseEntity<EstoqueResponse> registrarEntrada(
            @Valid @RequestBody RegistrarEstoqueRequest request
    ) {
        EstoqueResponse response =
                estoqueService.registrarEntrada(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/saldo")
    public ResponseEntity<Integer> consultarSaldo(
            @RequestParam UUID produtoId,
            @RequestParam UUID unidadeId
    ) {
        int saldo = estoqueService.consultarSaldo(
                produtoId,
                unidadeId
        );

        return ResponseEntity.ok(saldo);
    }
}
