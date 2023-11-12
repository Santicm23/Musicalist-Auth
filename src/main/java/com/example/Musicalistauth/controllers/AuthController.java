package com.example.Musicalistauth.controllers;

import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import com.example.Musicalistauth.exceptions.StandardRequestException;
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
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDTO) throws URISyntaxException, IOException, InterruptedException, StandardRequestException {
        return authService.getJWT(loginDTO);
    }

    @PostMapping(value = "/public/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponseDTO signup(@RequestBody UsuarioDTO usuarioDTO) throws StandardRequestException, IOException, URISyntaxException, InterruptedException {
        return authService.createUsuario(usuarioDTO);
    }

    @PostMapping(value = "/reniew", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponseDTO reniewJWT(@RequestHeader(name="Authorization") String token) throws URISyntaxException, IOException, InterruptedException, StandardRequestException {
        return authService.reniewJWT(token);
    }

    @PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
    public InfoUsuarioDTO getInfoUsuario(@RequestHeader(name="Authorization") String token) {
        return authService.getInfoUsuario(token);
    }
}
