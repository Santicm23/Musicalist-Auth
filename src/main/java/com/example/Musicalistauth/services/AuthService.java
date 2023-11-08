package com.example.Musicalistauth.services;

import com.example.Musicalistauth.config.JwtAuthenticationFilter;
import com.example.Musicalistauth.config.JwtProviderImpl;
import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import com.example.Musicalistauth.exceptions.StandardRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    public AuthService() {}

    public LoginResponseDTO getJWT(LoginRequestDTO loginRequestDTO) throws URISyntaxException, IOException, InterruptedException, StandardRequestException {
        JwtProviderImpl jwtProvider = new JwtProviderImpl();

        InfoUsuarioDTO infoUsuarioDTO = userService.getInfoUsuario(loginRequestDTO);

        return new LoginResponseDTO(
                jwtProvider.generateToken(infoUsuarioDTO.getId(), infoUsuarioDTO.getAdmin()),
                JwtAuthenticationFilter.PREFIX);
    }

    public LoginResponseDTO createUsuario(UsuarioDTO usuarioDTO) throws StandardRequestException, IOException, InterruptedException, URISyntaxException {
        String correo = usuarioDTO.getCorreo();
        String contrasena = usuarioDTO.getContrasena();

        userService.createUsuario(usuarioDTO);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(correo, contrasena);
        return getJWT(loginRequestDTO);
    }

    public InfoUsuarioDTO getInfoUsuario(String token) {
        JwtProviderImpl jwtProvider = new JwtProviderImpl();

        token = token.replace(JwtAuthenticationFilter.PREFIX, "");

        Long id = jwtProvider.extractId(token);
        Boolean admin = jwtProvider.extractAdmin(token);

        return new InfoUsuarioDTO(id, admin);
    }
}
