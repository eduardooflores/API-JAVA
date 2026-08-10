package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.CriarPedidoRequest;
import com.raizes.raizesdonordeste.application.dto.ItemPedidoRequest;
import com.raizes.raizesdonordeste.application.dto.ItemPedidoResponse;
import com.raizes.raizesdonordeste.application.dto.PedidoResponse;
import com.raizes.raizesdonordeste.application.exception.EstoqueInsuficienteException;
import com.raizes.raizesdonordeste.application.exception.RecursoNaoEncontradoException;
import com.raizes.raizesdonordeste.domain.enums.TipoMovimentacaoEstoque;
import com.raizes.raizesdonordeste.domain.model.Estoque;
import com.raizes.raizesdonordeste.domain.model.ItemPedido;
import com.raizes.raizesdonordeste.domain.model.Pedido;
import com.raizes.raizesdonordeste.domain.model.ProdutoUnidade;
import com.raizes.raizesdonordeste.domain.model.Unidade;
import com.raizes.raizesdonordeste.domain.model.Usuario;
import com.raizes.raizesdonordeste.infrastructure.repository.EstoqueRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.PedidoRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.ProdutoUnidadeRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.UnidadeRepository;
import com.raizes.raizesdonordeste.infrastructure.security.UsuarioDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoUnidadeRepository produtoUnidadeRepository;
    private final EstoqueRepository estoqueRepository;
    private final UnidadeRepository unidadeRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ProdutoUnidadeRepository produtoUnidadeRepository,
            EstoqueRepository estoqueRepository,
            UnidadeRepository unidadeRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.produtoUnidadeRepository = produtoUnidadeRepository;
        this.estoqueRepository = estoqueRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public PedidoResponse criarPedido(CriarPedidoRequest request) {

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Unidade não encontrada."
                        )
                );

        List<ItemPedido> itensPedido = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequest itemRequest : request.itens()) {

            ProdutoUnidade produtoUnidade =
                    produtoUnidadeRepository
                            .findByUnidadeIdAndProdutoIdAndDisponivelTrue(
                                    request.unidadeId(),
                                    itemRequest.produtoId()
                            )
                            .orElseThrow(() ->
                                    new RecursoNaoEncontradoException(
                                            "Produto não encontrado ou indisponível para esta unidade."
                                    )
                            );

            int saldoDisponivel = calcularSaldoEstoque(
                    itemRequest.produtoId(),
                    request.unidadeId()
            );

            if (saldoDisponivel < itemRequest.quantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto."
                );
            }

            ItemPedido itemPedido = new ItemPedido();

            itemPedido.setProduto(produtoUnidade.getProduto());
            itemPedido.setQuantidade(itemRequest.quantidade());
            itemPedido.setPrecoUnitario(produtoUnidade.getPreco());

            itensPedido.add(itemPedido);

            BigDecimal subtotal = produtoUnidade.getPreco()
                    .multiply(BigDecimal.valueOf(itemRequest.quantidade()));

            total = total.add(subtotal);
        }

        Usuario usuario = getUsuarioAutenticado();

        Pedido pedido = new Pedido();

        pedido.setUnidade(unidade);
        pedido.setCliente(usuario);
        pedido.setCanalPedido(request.canalPedido());
        pedido.setTotal(total);
        pedido.setItens(itensPedido);

        for (ItemPedido item : itensPedido) {
            item.setPedido(pedido);
        }

        pedidoRepository.save(pedido);

        for (ItemPedido item : itensPedido) {

            Estoque movimentacao = new Estoque();

            movimentacao.setProduto(item.getProduto());
            movimentacao.setUnidade(unidade);
            movimentacao.setTipoMovimentacaoEstoque(
                    TipoMovimentacaoEstoque.SAIDA
            );
            movimentacao.setQuantidade(item.getQuantidade());

            estoqueRepository.save(movimentacao);
        }

        List<ItemPedidoResponse> itensResponse = new ArrayList<>();

        for (ItemPedido item : itensPedido) {
            ItemPedidoResponse itemResponse = new ItemPedidoResponse(
                    item.getProduto().getId(),
                    item.getQuantidade(),
                    item.getPrecoUnitario()
            );

            itensResponse.add(itemResponse);

        }

        return new PedidoResponse(
                pedido.getId(),
                pedido.getStatusPedido(),
                pedido.getCanalPedido(),
                pedido.getTotal(),
                itensResponse,
                pedido.getCreatedAt()
        );

    }

    private int calcularSaldoEstoque(UUID produtoId, UUID unidadeId) {

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

    private Usuario getUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UsuarioDetails usuarioDetails =
                (UsuarioDetails) authentication.getPrincipal();

        return usuarioDetails.getUsuario();
    }
}
