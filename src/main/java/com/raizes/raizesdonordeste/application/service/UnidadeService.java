package com.raizes.raizesdonordeste.application.service;

import com.raizes.raizesdonordeste.application.dto.CriarUnidadeRequest;
import com.raizes.raizesdonordeste.application.dto.UnidadeResponse;
import com.raizes.raizesdonordeste.application.exception.RecursoNaoEncontradoException;
import com.raizes.raizesdonordeste.domain.model.Unidade;
import com.raizes.raizesdonordeste.infrastructure.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public UnidadeResponse criar(CriarUnidadeRequest request) {

        Unidade unidade = new Unidade();

        unidade.setNome(request.nome());
        unidade.setEndereco(request.endereco());

        unidadeRepository.save(unidade);

        return converterParaResponse(unidade);
    }

    public List<UnidadeResponse> listar() {

        return unidadeRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UnidadeResponse buscarPorId(UUID id) {

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Unidade não encontrada."
                        )
                );

        return converterParaResponse(unidade);
    }

    private UnidadeResponse converterParaResponse(Unidade unidade) {

        return new UnidadeResponse(
                unidade.getId(),
                unidade.getNome(),
                unidade.getEndereco(),
                unidade.isAtiva()
        );
    }
}