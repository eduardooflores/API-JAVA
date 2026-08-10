package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.ProdutoUnidadeResponse;
import com.raizes.raizesdonordeste.application.dto.VincularProdutoUnidadeRequest;
import com.raizes.raizesdonordeste.application.service.ProdutoUnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto-unidade")
public class ProdutoUnidadeController {

    private final ProdutoUnidadeService produtoUnidadeService;

    public ProdutoUnidadeController(
            ProdutoUnidadeService produtoUnidadeService
    ) {
        this.produtoUnidadeService = produtoUnidadeService;
    }

    @PostMapping
    public ResponseEntity<ProdutoUnidadeResponse> vincular(
            @Valid @RequestBody VincularProdutoUnidadeRequest request
    ) {
        ProdutoUnidadeResponse response =
                produtoUnidadeService.vincular(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<ProdutoUnidadeResponse>> listarPorUnidade(
            @PathVariable UUID unidadeId
    ) {
        return ResponseEntity.ok(
                produtoUnidadeService.listarPorUnidade(unidadeId)
        );
    }
}