
package com.raizes.raizesdonordeste.application.exception;
import com.raizes.raizesdonordeste.application.dto.DetalheErro;
import com.raizes.raizesdonordeste.application.dto.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request
    ) {
        ErroResponse erro = criarErro(
                "RECURSO_NAO_ENCONTRADO",
                ex.getMessage(),
                null,
                request
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErroResponse> tratarEstoqueInsuficiente(
            EstoqueInsuficienteException ex,
            HttpServletRequest request
    ) {
        ErroResponse erro = criarErro(
                "ESTOQUE_INSUFICIENTE",
                ex.getMessage(),
                null,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    @ExceptionHandler(TransicaoInvalidaException.class)
    public ResponseEntity<ErroResponse> tratarTransicaoInvalida(
            TransicaoInvalidaException ex,
            HttpServletRequest request
    ) {
        ErroResponse erro = criarErro(
                "TRANSICAO_INVALIDA",
                ex.getMessage(),
                null,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<DetalheErro> detalhes = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    detalhes.add(
                            new DetalheErro(
                                    fieldError.getField(),
                                    fieldError.getDefaultMessage()
                            )
                    );
                });

        ErroResponse erro = criarErro(
                "DADOS_INVALIDOS",
                "Os dados enviados são inválidos.",
                detalhes,
                request
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponse> tratarCredenciaisInvalidas(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        ErroResponse erro = criarErro(
                "CREDENCIAIS_INVALIDAS",
                "Email ou senha inválidos.",
                null,
                request
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroGenerico(
            Exception ex,
            HttpServletRequest request
    ) {
        ErroResponse erro = criarErro(
                "ERRO_INTERNO",
                "Erro interno no servidor.",
                null,
                request
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }

    private ErroResponse criarErro(
            String error,
            String message,
            List<DetalheErro> details,
            HttpServletRequest request
    ) {
        return new ErroResponse(
                error,
                message,
                details,
                LocalDateTime.now(),
                request.getRequestURI(),
                UUID.randomUUID().toString()
        );
    }
}
