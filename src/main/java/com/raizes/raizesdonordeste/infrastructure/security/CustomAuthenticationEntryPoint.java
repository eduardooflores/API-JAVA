package com.raizes.raizesdonordeste.infrastructure.security;

import tools.jackson.databind.ObjectMapper;
import com.raizes.raizesdonordeste.application.dto.ErroResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErroResponse erroResponse = new ErroResponse(
                "NAO_AUTENTICADO",
                authException.getMessage(),
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