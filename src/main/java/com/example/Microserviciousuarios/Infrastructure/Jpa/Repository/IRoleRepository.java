package com.example.Microserviciousuarios.Infrastructure.Jpa.Repository;

import com.example.Microserviciousuarios.Infrastructure.Jpa.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
