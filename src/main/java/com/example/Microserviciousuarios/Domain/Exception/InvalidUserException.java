package com.example.Microserviciousuarios.Domain.Exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
