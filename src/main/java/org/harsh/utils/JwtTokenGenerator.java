package org.harsh.utils;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtTokenGenerator {
    public String generateToken(String email) {
        Set<String> roles = Set.of("user");
        return Jwt.issuer("http://localhost:8080")
                .subject(email)
                .groups(roles)
                .expiresIn(3600) // 1month
                .sign();

    }
}
