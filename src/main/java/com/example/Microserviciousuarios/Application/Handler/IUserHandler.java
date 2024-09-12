package com.example.Microserviciousuarios.Application.Handler;

import com.example.Microserviciousuarios.Application.Dto.Request.RegisterRequest;

public interface IUserHandler {
    void createUser(RegisterRequest registerRequest);
}
