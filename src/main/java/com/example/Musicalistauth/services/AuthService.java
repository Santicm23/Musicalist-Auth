package com.example.Musicalistauth.services;

import com.example.Musicalistauth.config.JwtAuthenticationFilter;
import com.example.Musicalistauth.config.JwtProviderImpl;
import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthService() {}

    public LoginResponseDTO getJWT(LoginRequestDTO loginRequestDTO) {
        JwtProviderImpl jwtProveedorToken = new JwtProviderImpl();
        System.out.println(loginRequestDTO.getCorreo());
        System.out.println(loginRequestDTO.getContrasena());
        return new LoginResponseDTO(jwtProveedorToken.generateToken(1L, false), JwtAuthenticationFilter.PREFIX);
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
