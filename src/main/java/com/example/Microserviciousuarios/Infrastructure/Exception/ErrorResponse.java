package com.example.Microserviciousuarios.Infrastructure.Exception;

public class ErrorResponse extends RuntimeException {
    public ErrorResponse(String message) {
        super(message);
    }
}
