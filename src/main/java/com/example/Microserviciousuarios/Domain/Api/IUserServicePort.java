package com.example.Microserviciousuarios.Domain.Api;

import com.example.Microserviciousuarios.Domain.Models.User;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDate;

public interface IUserServicePort {
    void createUser(User user);

    boolean validateEmail(String email);

    String encryptPassword(String password);

    boolean validatePhone(String phone);

    boolean validateDocument(String document);

    boolean validateAge(LocalDate birthDate);
}
