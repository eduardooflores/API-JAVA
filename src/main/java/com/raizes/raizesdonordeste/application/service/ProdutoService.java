package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.CriarProdutoRequest;
import com.raizes.raizesdonordeste.application.dto.ProdutoResponse;
import com.raizes.raizesdonordeste.application.exception.RecursoNaoEncontradoException;
import com.raizes.raizesdonordeste.domain.model.Produto;
import com.raizes.raizesdonordeste.infrastructure.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponse criar(CriarProdutoRequest request) {

        Produto produto = new Produto();

        produto.setNome(request.nome());
        produto.setCategoria(request.categoria());
        produto.setPrecoBase(request.precoBase());

        produtoRepository.save(produto);

        return converterParaResponse(produto);
    }

    public List<ProdutoResponse> listar() {

        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public ProdutoResponse buscarPorId(UUID id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Produto não encontrado."
                        )
                );

        return converterParaResponse(produto);
    }

    private ProdutoResponse converterParaResponse(Produto produto) {

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getPrecoBase()
        );
    }
}