package com.example.Microserviciousuarios.Domain.Exception;

public class InvalidAgeException extends RuntimeException{
    public InvalidAgeException(String message) {
        super(message);
    }
}
