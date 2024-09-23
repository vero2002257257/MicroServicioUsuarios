package com.example.Microserviciousuarios.Infrastructure.Jpa.Adapter;




import com.example.Microserviciousuarios.Domain.Models.User;
import com.example.Microserviciousuarios.Domain.Spi.IUserPersistencePort;
import com.example.Microserviciousuarios.Infrastructure.Exception.DuplicateDocumentException;
import com.example.Microserviciousuarios.Infrastructure.Exception.DuplicateEmailException;
import com.example.Microserviciousuarios.Infrastructure.Exception.UserNotFoundException;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Entity.UserEntity;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Mapper.UserEntityMapper;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.Microserviciousuarios.Utils.Constants.*;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public void createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateEmailException(EMAIL_ALREADY_EXISTS);
        }
        if(userRepository.findByDocument(user.getDocument()).isPresent()){
            throw new DuplicateDocumentException(DOCUMENT_ALREADY_EXISTS);
        }

        UserEntity userEntity = userEntityMapper.toUserEntity(user);
        userEntityMapper.toUser(userRepository.save(userEntity));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
