package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.ProdutoUnidadeResponse;
import com.raizes.raizesdonordeste.application.dto.VincularProdutoUnidadeRequest;
import com.raizes.raizesdonordeste.application.exception.RecursoNaoEncontradoException;
import com.raizes.raizesdonordeste.domain.model.Produto;
import com.raizes.raizesdonordeste.domain.model.ProdutoUnidade;
import com.raizes.raizesdonordeste.domain.model.Unidade;
import com.raizes.raizesdonordeste.infrastructure.repository.ProdutoRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.ProdutoUnidadeRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoUnidadeService {

    private final ProdutoUnidadeRepository produtoUnidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;

    public ProdutoUnidadeService(
            ProdutoUnidadeRepository produtoUnidadeRepository,
            ProdutoRepository produtoRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.produtoUnidadeRepository = produtoUnidadeRepository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public ProdutoUnidadeResponse vincular(VincularProdutoUnidadeRequest request) {

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Produto não encontrado")
                );

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Unidade não encontrada")
                );

        ProdutoUnidade produtoUnidade = new ProdutoUnidade();

        produtoUnidade.setProduto(produto);
        produtoUnidade.setUnidade(unidade);
        produtoUnidade.setPreco(request.preco());
        produtoUnidade.setDisponivel(true);

        ProdutoUnidade salvo = produtoUnidadeRepository.save(produtoUnidade);

        return converterParaResponse(salvo);
    }

    public List<ProdutoUnidadeResponse> listarPorUnidade(UUID unidadeId) {

        return produtoUnidadeRepository
                .findByUnidadeIdAndDisponivelTrue(unidadeId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    private ProdutoUnidadeResponse converterParaResponse(
            ProdutoUnidade produtoUnidade
    ) {
        return new ProdutoUnidadeResponse(
                produtoUnidade.getId(),
                produtoUnidade.getProduto().getId(),
                produtoUnidade.getUnidade().getId(),
                produtoUnidade.getPreco(),
                produtoUnidade.isDisponivel()
        );
    }
}