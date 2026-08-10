package com.raizes.raizesdonordeste.api.controller;

import com.raizes.raizesdonordeste.application.dto.CriarUnidadeRequest;
import com.raizes.raizesdonordeste.application.dto.UnidadeResponse;
import com.raizes.raizesdonordeste.application.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {
    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping
    public ResponseEntity<UnidadeResponse> criar(
            @Valid @RequestBody CriarUnidadeRequest request
    ) {
        UnidadeResponse response = unidadeService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeResponse>> listar() {

        return ResponseEntity.ok(
                unidadeService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponse> buscarPorId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                unidadeService.buscarPorId(id)
        );
    }
}
