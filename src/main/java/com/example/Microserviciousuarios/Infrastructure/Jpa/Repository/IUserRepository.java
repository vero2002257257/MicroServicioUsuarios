package com.example.Microserviciousuarios.Infrastructure.Jpa.Repository;

import com.example.Microserviciousuarios.Infrastructure.Jpa.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByDocument(String document);
    Optional <UserEntity >findByName(String name);
}
