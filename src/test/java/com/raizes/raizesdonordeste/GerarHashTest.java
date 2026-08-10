package com.raizes.raizesdonordeste;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHashTest {

    @Test
    void gerarHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senha = "123456";
        String hash = encoder.encode(senha);

        System.out.println("Senha: " + senha);
        System.out.println("Hash: " + hash);
    }
}