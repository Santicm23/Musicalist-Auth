package com.example.Musicalistauth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Musicalistauth.services.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Generated;

@Component
public class JWTFiltroAutorizacion extends OncePerRequestFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    private final JWTProveedorToken jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Generated
    public JWTFiltroAutorizacion(JWTProveedorToken jwtTokenProvider, CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Generated
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (existeJWTToken(request)) {
                Claims claims = validarToken(request);
                if (claims.get("authorities") != null) {
                    System.out.println(claims.get("authorities"));
                    String username = getUsername(request);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, userDetails, null);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Generated
    private Claims validarToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        System.out.println("jwt: " + jwtToken);
        System.out.println("claims: " + jwtTokenProvider.getClaims(jwtToken));
        return jwtTokenProvider.getClaims(jwtToken);
    }
    @Generated
    private String getUsername(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        System.out.println("Username: " + jwtTokenProvider.getUsername(jwtToken));
        return jwtTokenProvider.getUsername(jwtToken);
    }
    @Generated
    private boolean existeJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX));
    }
}
