package com.example.Musicalistauth.config;

public interface JwtProvider {

    Long extractId(String token);

    String generateToken(Long id, Boolean admin);

    boolean isTokenValid(String token, Long uid);

    Boolean extractAdmin(String token);

    String extractAuthorities(String token);
}
