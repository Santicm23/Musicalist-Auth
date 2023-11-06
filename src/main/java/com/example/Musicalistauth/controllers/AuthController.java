package com.example.Musicalistauth.controllers;

import com.example.Musicalistauth.config.JWTFiltroAutorizacion;
import com.example.Musicalistauth.config.JWTProveedorToken;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping(value = "/public/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponseDTO login(@RequestParam( required = false ) String correo, @RequestParam( required = false ) String contrasena) {
        JWTProveedorToken jwtProveedorToken = new JWTProveedorToken();
        return new LoginResponseDTO(jwtProveedorToken.generateToken(correo), JWTFiltroAutorizacion.PREFIX) ;
    }

    @PostMapping(value = "/public/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponseDTO signup(@RequestParam( required = false ) String correo, @RequestParam( required = false ) String contrasena) {
        JWTProveedorToken jwtProveedorToken = new JWTProveedorToken();
        return new LoginResponseDTO(jwtProveedorToken.generateToken(correo), JWTFiltroAutorizacion.PREFIX) ;
    }
}
