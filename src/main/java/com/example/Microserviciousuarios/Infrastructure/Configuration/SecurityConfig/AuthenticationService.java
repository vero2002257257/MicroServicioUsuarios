package com.example.Microserviciousuarios.Infrastructure.Configuration.SecurityConfig;


import com.example.Microserviciousuarios.Application.Auth.AuthenticationRequest;
import com.example.Microserviciousuarios.Application.Auth.AuthenticationResponse;
import com.example.Microserviciousuarios.Domain.Exception.InvalidUserException;
import com.example.Microserviciousuarios.Infrastructure.Configuration.JwtConfiguration.JwtService;
import com.example.Microserviciousuarios.Infrastructure.Exception.AuthenticationException;
import com.example.Microserviciousuarios.Infrastructure.Exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.example.Microserviciousuarios.Utils.Constants.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            ProviderManager providerManager = new ProviderManager(Arrays.asList(authenticationProvider));

            Authentication authentication = providerManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
                if (userDetails == null) {
                    throw new UserNotFoundException(USER_NOT_FOUND);
                }
                String jwtToken = jwtService.generate(userDetails);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            } else {
                throw new InvalidUserException(USER_OR_PASSWORD_INCORRECT);
            }
        } catch (BadCredentialsException e) {
            throw new InvalidUserException(USER_OR_PASSWORD_INCORRECT);
        } catch (AuthenticationException e) {
            throw new InvalidUserException(INVALID_AUTH);
        }
    }
}
