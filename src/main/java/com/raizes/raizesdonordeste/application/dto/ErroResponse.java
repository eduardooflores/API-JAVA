package com.raizes.raizesdonordeste.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        String error,
        String message,
        List<DetalheErro> details,
        LocalDateTime timestamp,
        String path,
        String requestId
) {}
