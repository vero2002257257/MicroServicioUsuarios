package com.example.Microserviciousuarios.Infrastructure.Jpa.Adapter;

import com.example.Microserviciousuarios.Domain.Models.Role;
import com.example.Microserviciousuarios.Domain.Spi.IRolPersistencePort;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Repository.IRoleRepository;

import java.util.Optional;
public class RoleJpaAdapter implements IRolPersistencePort {

    private final IRoleRepository roleRepository;


    public RoleJpaAdapter(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getRolByName(String role) {
        return roleRepository.findByName(role)
                .map(roleEntity -> new Role(roleEntity.getId(), roleEntity.getName()));
    }
}
