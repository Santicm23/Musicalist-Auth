package com.example.Musicalistauth.services;

import com.example.Musicalistauth.config.JWTFiltroAutorizacion;
import com.example.Musicalistauth.config.JWTProveedorToken;
import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthService() {}

    public LoginResponseDTO getJWT(LoginRequestDTO loginRequestDTO) {
        JWTProveedorToken jwtProveedorToken = new JWTProveedorToken();
        System.out.println(loginRequestDTO.getCorreo());
        System.out.println(loginRequestDTO.getContrasena());
        return new LoginResponseDTO(jwtProveedorToken.generateToken(loginRequestDTO.getCorreo()), JWTFiltroAutorizacion.PREFIX);
    }

    public LoginResponseDTO createUsuario(UsuarioDTO usuarioDTO) {
        JWTProveedorToken jwtProveedorToken = new JWTProveedorToken();
        return new LoginResponseDTO(jwtProveedorToken.generateToken(usuarioDTO.getCorreo()), JWTFiltroAutorizacion.PREFIX);
    }

    public InfoUsuarioDTO getInfoUsuario(String token) {
        JWTProveedorToken jwtProveedorToken = new JWTProveedorToken();

        token = token.replace(JWTFiltroAutorizacion.PREFIX, "");

        jwtProveedorToken.getUsername(token);
        System.out.println("getInfoUsuario");
        return null;
    }


}
