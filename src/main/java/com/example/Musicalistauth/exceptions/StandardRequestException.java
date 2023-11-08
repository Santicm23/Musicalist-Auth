package com.example.Musicalistauth.exceptions;

import java.io.Serial;

public class StandardRequestException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public StandardRequestException(String mensaje) {
        super(mensaje);
    }
}

