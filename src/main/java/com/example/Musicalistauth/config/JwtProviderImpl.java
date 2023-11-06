package com.example.Musicalistauth.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtProviderImpl implements JwtProvider {

    private static final Key key = generateKey();

    private final long jwtExpirationDate;


    public JwtProviderImpl(){
        jwtExpirationDate = 604800000;
    }

    @Override
    public String generateToken(Long id, Boolean admin) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);


        Map<String, Object>authoritiesClaim = new HashMap<>();
        authoritiesClaim.put("authorities", new SimpleGrantedAuthority(admin ? "ADMIN" : "USER"));

        return Jwts.builder()
                .addClaims(authoritiesClaim)
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    @Override
    public Long extractId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    @Override
    public boolean isTokenValid(String token, Long uid) {
        final Long id = extractId(token);
        return (id.equals(uid)) && !isTokenExpired(token);
    }

    @Override
    public String extractAuthorities(String token) {
        return extractAllClaims(token).get("authorities").toString();
    }

    private static Key generateKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
