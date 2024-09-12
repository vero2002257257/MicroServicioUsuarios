package com.example.Microserviciousuarios.Application.Mapper;

import com.example.Microserviciousuarios.Application.Dto.Request.RegisterRequest;
import com.example.Microserviciousuarios.Domain.Models.Role;
import com.example.Microserviciousuarios.Domain.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface IUserRequestMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "document", source = "document")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "role", source = "role")
    User toUser(RegisterRequest registerRequest);

    default Role map(String roleName) {
        if (roleName == null || roleName.isEmpty()) {
            return null;
        }
        Role role = new Role();
        role.setName(roleName);
        return role;
    }
}
