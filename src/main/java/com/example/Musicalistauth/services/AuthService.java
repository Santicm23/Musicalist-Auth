package com.example.Musicalistauth.services;

import com.example.Musicalistauth.config.JwtAuthenticationFilter;
import com.example.Musicalistauth.config.JwtProviderImpl;
import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AuthService {

    public AuthService() {}

    public LoginResponseDTO getJWT(LoginRequestDTO loginRequestDTO) throws URISyntaxException, IOException, InterruptedException {
        JwtProviderImpl jwtProveedorToken = new JwtProviderImpl();
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(loginRequestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return new LoginResponseDTO(response.body(), JwtAuthenticationFilter.PREFIX);
        } else {
            throw new RuntimeException("Error al iniciar sesión");
        }

        //return new LoginResponseDTO(jwtProveedorToken.generateToken(1L, false), JwtAuthenticationFilter.PREFIX);
    }

    public LoginResponseDTO createUsuario(UsuarioDTO usuarioDTO) {
        JwtProviderImpl jwtProveedorToken = new JwtProviderImpl();
        return new LoginResponseDTO(jwtProveedorToken.generateToken(1L, false), JwtAuthenticationFilter.PREFIX);
    }

    public InfoUsuarioDTO getInfoUsuario(String token) {
        JwtProviderImpl jwtProveedorToken = new JwtProviderImpl();

        token = token.replace(JwtAuthenticationFilter.PREFIX, "");

        jwtProveedorToken.extractId(token);
        return null;
    }


}
