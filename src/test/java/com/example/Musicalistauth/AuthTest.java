package com.example.Musicalistauth;

import com.example.Musicalistauth.dtos.InfoUsuarioDTO;
import com.example.Musicalistauth.dtos.LoginRequestDTO;
import com.example.Musicalistauth.dtos.LoginResponseDTO;
import com.example.Musicalistauth.dtos.UsuarioDTO;
import com.example.Musicalistauth.exceptions.StandardRequestException;
import com.example.Musicalistauth.services.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTest {

    @Autowired
    AuthService authService;

    private UsuarioDTO usuarioDTO;

    private LoginResponseDTO loginResponseDTO;

    @BeforeEach
    @Test
    public void testCreateUsuario() throws StandardRequestException, IOException, URISyntaxException, InterruptedException {
        usuarioDTO = new UsuarioDTO("Test", "prueba@prueba.com", "123456");

//        authService.createUsuario(usuarioDTO);

    }

    @Test
    @Order(1)
    public void testGetJWT() throws URISyntaxException, IOException, InterruptedException, StandardRequestException {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(usuarioDTO.getCorreo(), usuarioDTO.getContrasena());
        loginResponseDTO = authService.getJWT(loginRequestDTO);

        Assertions.assertNotNull(loginResponseDTO);
    }

    @Test
    @Order(2)
    public void testReniewJWT() throws StandardRequestException, URISyntaxException, IOException, InterruptedException {
        testGetJWT();
        LoginResponseDTO loginResponseDTO2 = authService.reniewJWT(loginResponseDTO.getToken());

        Assertions.assertNotNull(loginResponseDTO2);
    }

    @Test
    @Order(3)
    public void testGetInfoUsuario() throws StandardRequestException, URISyntaxException, IOException, InterruptedException {
        testGetJWT();
        InfoUsuarioDTO infoUsuarioDTO = authService.getInfoUsuario(loginResponseDTO.getToken());

        Assertions.assertNotNull(infoUsuarioDTO);
    }
}
