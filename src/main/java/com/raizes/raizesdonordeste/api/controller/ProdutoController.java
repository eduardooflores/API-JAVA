package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.CriarProdutoRequest;
import com.raizes.raizesdonordeste.application.dto.ProdutoResponse;
import com.raizes.raizesdonordeste.application.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(
            @Valid @RequestBody CriarProdutoRequest request
    ) {
        ProdutoResponse response = produtoService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {

        return ResponseEntity.ok(
                produtoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                produtoService.buscarPorId(id)
        );
    }
}
