package com.example.Musicalistauth.exceptions;

import java.util.Calendar;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(StandardRequestException.class)
    public ResponseEntity<RequestErrorMessage> exception(StandardRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        RequestErrorMessage mensajeError = new RequestErrorMessage(
                ex.getMessage(), Calendar.getInstance().getTime(), status);

        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(mensajeError, responseHeaders, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RequestErrorMessage> exception(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        System.err.println(ex.getMessage());
        RequestErrorMessage mensajeError = new RequestErrorMessage(
                ex.getMessage(), Calendar.getInstance().getTime(), status);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(mensajeError, responseHeaders, status);
    }
}
