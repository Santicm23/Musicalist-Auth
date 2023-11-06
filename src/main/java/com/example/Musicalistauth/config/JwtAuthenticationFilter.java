package com.example.Musicalistauth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Generated;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @Generated
    public JwtAuthenticationFilter(JwtProvider jwtTokenProvider) {
        this.jwtProvider = jwtTokenProvider;
    }

    @Generated
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (!existJwt(request.getHeader(HEADER))) {
                chain.doFilter(request, response);
                return;
            }
            String jwt = getJwt(request);
            if (jwtProvider.isTokenValid(jwt, jwtProvider.extractId(jwt))) {
                if (jwtProvider.extractAuthorities(jwt) != null) {
                    System.out.println(jwtProvider.extractAuthorities(jwt));
                    Long id = jwtProvider.extractId(jwt);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, null, null);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Generated
    private boolean existJwt(String authHeader) {
        return !(authHeader == null || !authHeader.startsWith(PREFIX));
    }

    @Generated
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER);
        if (existJwt(authHeader)) {
            return authHeader.replace(PREFIX, "");
        }
        throw new MalformedJwtException("No existe el token");
    }
}
