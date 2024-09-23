package com.example.Microserviciousuarios.Infrastructure.Exception;

public class DuplicateDocumentException extends RuntimeException {
    public DuplicateDocumentException(String message) {
        super(message);
    }
}
