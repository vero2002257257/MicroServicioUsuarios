package com.example.Microserviciousuarios.Application.Handler;

import com.example.Microserviciousuarios.Application.Dto.Request.RegisterRequest;
import com.example.Microserviciousuarios.Application.Mapper.IUserRequestMapper;
import com.example.Microserviciousuarios.Domain.Api.IUserServicePort;
import com.example.Microserviciousuarios.Domain.Models.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    public void createUser(RegisterRequest registerRequest) {
        User user = userRequestMapper.toUser(registerRequest);
        userServicePort.createUser(user);
    }
}
