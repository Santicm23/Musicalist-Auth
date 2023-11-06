package com.example.Musicalistauth.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import static org.apache.tomcat.jni.SSLConf.apply;


@Component
public class JWTProveedorToken {

    private static final Key key = generateKey();

    private final long jwtExpirationDate;


    public JWTProveedorToken(){
        jwtExpirationDate = 604800000;
    }

    // generate JWT token
    public String generateToken(String username) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);


        Map<String, Object>authoritiesClaim = new HashMap<>();
        authoritiesClaim.put("authorities", new SimpleGrantedAuthority("Admin"));

        return Jwts.builder()
                .addClaims(authoritiesClaim)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    private static Key generateKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // get username from Jwt token
    public String getUsername(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody();
    }

}
