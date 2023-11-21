package com.example.Musicalistauth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDTO {

    private String nombre;
    private String correo;
    private String contrasena;
}
