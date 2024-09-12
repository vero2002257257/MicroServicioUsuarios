package com.example.Microserviciousuarios.Domain.Spi;

import com.example.Microserviciousuarios.Domain.Models.Role;
import java.util.Optional;

public interface IRolPersistencePort {
    Optional<Role> getRolByName(String role);
}
