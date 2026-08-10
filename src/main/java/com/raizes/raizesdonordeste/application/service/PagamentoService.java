package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.PagamentoResponse;
import com.raizes.raizesdonordeste.application.exception.RecursoNaoEncontradoException;
import com.raizes.raizesdonordeste.domain.enums.StatusPagamento;
import com.raizes.raizesdonordeste.domain.enums.StatusPedido;
import com.raizes.raizesdonordeste.domain.model.Pagamento;
import com.raizes.raizesdonordeste.domain.model.Pedido;
import com.raizes.raizesdonordeste.infrastructure.repository.PagamentoRepository;
import com.raizes.raizesdonordeste.infrastructure.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            PedidoRepository pedidoRepository
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public PagamentoResponse processarPagamento(UUID pedidoId) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado."
                        )
                );

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);

        boolean aprovado = Math.random() > 0.2;

        if (aprovado) {

            pagamento.setStatusPagamento(
                    StatusPagamento.APROVADO
            );

            pagamento.setValorPago(
                    pedido.getTotal()
            );

            pagamento.setPayload(
                    "Pagamento aprovado - gatewayMockId="
                            + UUID.randomUUID()
            );

            pedido.setStatusPedido(
                    StatusPedido.EM_PREPARO
            );

        } else {

            pagamento.setStatusPagamento(
                    StatusPagamento.RECUSADO
            );

            pagamento.setValorPago(
                    BigDecimal.ZERO
            );

            pagamento.setPayload(
                    "Pagamento recusado - gatewayMockId="
                            + UUID.randomUUID()
            );
        }

        pagamentoRepository.save(pagamento);

        if (aprovado) {
            pedidoRepository.save(pedido);
        }

        return new PagamentoResponse(
                pagamento.getId(),
                pedido.getId(),
                pagamento.getStatusPagamento(),
                pagamento.getValorPago(),
                pagamento.getPayload(),
                pagamento.getDataPagamento()
        );
    }
}
