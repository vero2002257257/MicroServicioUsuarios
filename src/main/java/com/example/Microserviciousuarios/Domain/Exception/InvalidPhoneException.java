package com.example.Microserviciousuarios.Domain.Exception;

public class InvalidPhoneException extends RuntimeException {
    public InvalidPhoneException(String message) {
        super(message);
    }
}
