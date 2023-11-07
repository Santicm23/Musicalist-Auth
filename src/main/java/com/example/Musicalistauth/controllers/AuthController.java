package com.example.Musicalistauth.controllers;

import com.example.Musicalistauth.config.JwtAuthenticationFilter;
import com.example.Musicalistauth.config.JwtProviderImpl;
import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = "/public/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDTO) throws URISyntaxException, IOException, InterruptedException {
        return authService.getJWT(loginDTO);
    }

    @PostMapping(value = "/public/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponseDTO signup(@RequestParam( required = false ) String correo, @RequestParam( required = false ) String contrasena) {
        JwtProviderImpl jwtProveedorToken = new JwtProviderImpl();
        return new LoginResponseDTO(jwtProveedorToken.generateToken(1L, false), JwtAuthenticationFilter.PREFIX);
    }

    @PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:8080")
    public InfoUsuarioDTO getInfoUsuario(@RequestHeader(name="Authorization") String token) {
        return authService.getInfoUsuario(token);
    }
}
