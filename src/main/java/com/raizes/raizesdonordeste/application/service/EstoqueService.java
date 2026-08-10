package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.EstoqueResponse;
import com.raizes.raizesdonordeste.application.dto.RegistrarEstoqueRequest;
import com.raizes.raizesdonordeste.application.exception.RecursoNaoEncontradoException;
import com.raizes.raizesdonordeste.domain.enums.TipoMovimentacaoEstoque;
import com.raizes.raizesdonordeste.domain.model.Estoque;
import com.raizes.raizesdonordeste.domain.model.Produto;
import com.raizes.raizesdonordeste.domain.model.Unidade;
import com.raizes.raizesdonordeste.infrastructure.repository.EstoqueRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.ProdutoRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;

    public EstoqueService(
            EstoqueRepository estoqueRepository,
            ProdutoRepository produtoRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public EstoqueResponse registrarEntrada(
            RegistrarEstoqueRequest request
    ) {

        Produto produto = produtoRepository.findById(
                request.produtoId()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Produto não encontrado."
                )
        );

        Unidade unidade = unidadeRepository.findById(
                request.unidadeId()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Unidade não encontrada."
                )
        );

        Estoque estoque = new Estoque();

        estoque.setProduto(produto);
        estoque.setUnidade(unidade);
        estoque.setTipoMovimentacaoEstoque(
                TipoMovimentacaoEstoque.ENTRADA
        );
        estoque.setQuantidade(request.quantidade());

        estoqueRepository.save(estoque);

        return converterParaResponse(estoque);
    }

    public int consultarSaldo(
            UUID produtoId,
            UUID unidadeId
    ) {

        List<Estoque> movimentacoes =
                estoqueRepository.findByProdutoIdAndUnidadeId(
                        produtoId,
                        unidadeId
                );

        int saldo = 0;

        for (Estoque movimentacao : movimentacoes) {

            if (movimentacao.getTipoMovimentacaoEstoque()
                    == TipoMovimentacaoEstoque.ENTRADA) {

                saldo += movimentacao.getQuantidade();

            } else if (movimentacao.getTipoMovimentacaoEstoque()
                    == TipoMovimentacaoEstoque.SAIDA) {

                saldo -= movimentacao.getQuantidade();
            }
        }

        return saldo;
    }

    private EstoqueResponse converterParaResponse(
            Estoque estoque
    ) {

        return new EstoqueResponse(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getUnidade().getId(),
                estoque.getTipoMovimentacaoEstoque(),
                estoque.getQuantidade(),
                estoque.getDataMovimentacao()
        );
    }
}
