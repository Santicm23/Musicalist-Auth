package com.example.Musicalistauth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InfoUsuarioDTO {
    private Long id;
    private Boolean admin;
}
