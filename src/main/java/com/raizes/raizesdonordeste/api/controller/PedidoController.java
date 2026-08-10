package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.CriarPedidoRequest;
import com.raizes.raizesdonordeste.application.dto.PedidoResponse;
import com.raizes.raizesdonordeste.application.service.PedidoService;
import com.raizes.raizesdonordeste.domain.enums.CanalPedido;
import com.raizes.raizesdonordeste.domain.enums.StatusPedido;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(
            @Valid @RequestBody CriarPedidoRequest request
    ) {
        PedidoResponse response = pedidoService.criarPedido(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar(
            @RequestParam(required = false) CanalPedido canalPedido,
            @RequestParam(required = false) StatusPedido status
    ) {
        List<PedidoResponse> response =
                pedidoService.listar(canalPedido, status);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable UUID id,
            @RequestParam StatusPedido novoStatus
    ) {
        PedidoResponse response =
                pedidoService.atualizarStatus(id, novoStatus);

        return ResponseEntity.ok(response);
    }
}
