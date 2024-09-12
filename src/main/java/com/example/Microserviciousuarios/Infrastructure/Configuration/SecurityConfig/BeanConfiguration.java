package com.example.Microserviciousuarios.Infrastructure.Configuration.SecurityConfig;

import com.example.Microserviciousuarios.Domain.Api.IUserServicePort;
import com.example.Microserviciousuarios.Domain.Spi.IRolPersistencePort;
import com.example.Microserviciousuarios.Domain.Spi.IUserPersistencePort;
import com.example.Microserviciousuarios.Domain.UseCase.UserUseCase;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Adapter.RoleJpaAdapter;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Adapter.UserJpaAdapter;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Mapper.UserEntityMapper;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Repository.IRoleRepository;
import com.example.Microserviciousuarios.Infrastructure.Jpa.Repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.Microserviciousuarios.Utils.Constants.USER_NOT_FOUND;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRoleRepository rolRepository;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }



    @Bean
    public IUserServicePort userServicePort(@Lazy IUserPersistencePort userPersistencePort, @Lazy IRolPersistencePort rolPersistencePort) {
        return new UserUseCase(userPersistencePort, rolPersistencePort);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IRolPersistencePort rolPersistencePort() {
        return new RoleJpaAdapter(rolRepository);
    }
}
