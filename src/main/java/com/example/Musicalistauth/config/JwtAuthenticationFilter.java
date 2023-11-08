package com.example.Musicalistauth.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Musicalistauth.exceptions.RequestErrorMessage;
import com.google.gson.Gson;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
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
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            RequestErrorMessage message = new RequestErrorMessage("Token inválido", Calendar.getInstance().getTime(), HttpStatus.FORBIDDEN);
            sendCustomError(response, message);
        } catch (ExpiredJwtException e) {
            RequestErrorMessage message = new RequestErrorMessage("Token expirado", Calendar.getInstance().getTime(), HttpStatus.UNAUTHORIZED);
            sendCustomError(response, message);
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

    @Generated
    private void sendCustomError(HttpServletResponse response, RequestErrorMessage message) throws IOException {
        Gson gson = new Gson();
        String messageString = gson.toJson(message);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        PrintWriter out = response.getWriter();
        out.print(messageString);
        out.flush();
    }
}
