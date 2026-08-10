package com.raizes.raizesdonordeste.infrastructure.security;

import tools.jackson.databind.ObjectMapper;
import com.raizes.raizesdonordeste.application.dto.ErroResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErroResponse erroResponse = new ErroResponse(
                "SEM_PERMISSAO",
                accessDeniedException.getMessage(),
                null,
                LocalDateTime.now(),
                request.getRequestURI(),
                UUID.randomUUID().toString()
        );

        response.getWriter().write(
                objectMapper.writeValueAsString(erroResponse)
        );
    }
}