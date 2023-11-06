package com.example.Musicalistauth.config;

import com.example.Musicalistauth.dtos.InfoUsuarioDTO;

public interface JwtProvider {

    Long extractId(String token);

    String generateToken(Long id, Boolean admin);

    boolean isTokenValid(String token, Long uid);

    String extractAuthorities(String token);
}
