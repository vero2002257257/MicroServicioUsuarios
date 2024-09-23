package com.example.Microserviciousuarios.Domain.Spi;

import com.example.Microserviciousuarios.Domain.Models.User;

public interface IUserPersistencePort {
    User getUserByEmail(String email);
    void createUser(User user);
}
