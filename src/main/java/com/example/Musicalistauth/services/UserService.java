package com.example.Musicalistauth.services;

import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import com.example.Musicalistauth.exceptions.RequestErrorMessage;
import com.example.Musicalistauth.exceptions.StandardRequestException;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserService {
    public UserService() {
    }

    public InfoUsuarioDTO getInfoUsuario(LoginRequestDTO loginRequestDTO) throws URISyntaxException, IOException, InterruptedException, StandardRequestException {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(loginRequestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (200 <= response.statusCode() && response.statusCode() < 300) {
            return gson.fromJson(response.body(), InfoUsuarioDTO.class);
        } else {
            RequestErrorMessage requestErrorMessage = gson.fromJson(response.body(), RequestErrorMessage.class);
            throw new StandardRequestException(requestErrorMessage.message);
        }
    }

    public void createUsuario(UsuarioDTO usuarioDTO) throws IOException, InterruptedException, StandardRequestException {
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(usuarioDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/usuario"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 && 300 <= response.statusCode()) {
            RequestErrorMessage requestErrorMessage = gson.fromJson(response.body(), RequestErrorMessage.class);
            throw new StandardRequestException(requestErrorMessage.message);
        }
    }
}
