package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.PagamentoResponse;
import com.raizes.raizesdonordeste.application.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/{pedidoId}")
    public ResponseEntity<PagamentoResponse> processarPagamento(
            @PathVariable UUID pedidoId
    ) {
        PagamentoResponse response =
                pagamentoService.processarPagamento(pedidoId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
