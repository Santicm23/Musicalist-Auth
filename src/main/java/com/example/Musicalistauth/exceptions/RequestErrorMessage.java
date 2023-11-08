package com.example.Musicalistauth.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class RequestErrorMessage {
    public String message;
    public Date timestamp;
    public Integer status;
    public String error;

    public RequestErrorMessage(String message, Date timestamp, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.timestamp = timestamp;
    }
}

