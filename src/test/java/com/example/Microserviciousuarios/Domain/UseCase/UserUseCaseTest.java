package com.example.Microserviciousuarios.Domain.UseCase;

import com.example.Microserviciousuarios.Domain.Exception.InvalidAgeException;
import com.example.Microserviciousuarios.Domain.Exception.InvalidDocumentException;
import com.example.Microserviciousuarios.Domain.Exception.InvalidEmailException;
import com.example.Microserviciousuarios.Domain.Exception.InvalidPhoneException;
import com.example.Microserviciousuarios.Domain.Models.Role;
import com.example.Microserviciousuarios.Domain.Models.User;
import com.example.Microserviciousuarios.Domain.Spi.IRolPersistencePort;
import com.example.Microserviciousuarios.Domain.Spi.IUserPersistencePort;
import com.example.Microserviciousuarios.Infrastructure.Exception.RoleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {


    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IRolPersistencePort rolPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully_whenUserDataIsValid() {
        User user = createUser();
        Role role = new Role();
        role.setName("USER");

        when(rolPersistencePort.getRolByName("USER")).thenReturn(Optional.of(role));

        userUseCase.createUser(user);

        verify(userPersistencePort).createUser(user);
    }

    @Test
    void shouldThrowInvalidEmailException_whenEmailIsInvalid() {
        User user = createUser();
        user.setEmail("invalid-email");

        assertThrows(InvalidEmailException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void shouldThrowInvalidPhoneException_whenPhoneIsInvalid() {
        User user = createUser();
        user.setPhone("wrong-phone");

        assertThrows(InvalidPhoneException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void shouldThrowInvalidDocumentException_whenDocumentIsInvalid() {
        User user = createUser();
        user.setDocument("wrong-doc");

        assertThrows(InvalidDocumentException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void shouldThrowInvalidAgeException_whenUserIsUnderage() {
        User user = createUser();
        user.setBirthDate(LocalDate.now().minusYears(16));

        assertThrows(InvalidAgeException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void shouldThrowRoleNotFoundException_whenRoleDoesNotExist() {
        User user = createUser();
        Role nonExistentRole = new Role();
        nonExistentRole.setName("NON_EXISTENT_ROLE");

        when(rolPersistencePort.getRolByName("NON_EXISTENT_ROLE")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userUseCase.createUser(user));
    }

    @Test
    void shouldReturnTrue_whenDocumentIsValid() {
        assertTrue(userUseCase.validateDocument("123456"));
    }

    @Test
    void shouldReturnFalse_whenDocumentIsInvalid() {
        assertFalse(userUseCase.validateDocument("wrong123"));
    }

    @Test
    void shouldReturnFalse_whenDocumentIsEmpty() {
        assertFalse(userUseCase.validateDocument(""));
    }

    @Test
    void shouldReturnTrue_whenAgeIsValid() {
        assertTrue(userUseCase.validateAge(LocalDate.now().minusYears(20)));
    }

    @Test
    void shouldReturnFalse_whenUserIsExactly18YearsOld() {
        assertFalse(userUseCase.validateAge(LocalDate.now().minusYears(18)));
    }

    @Test
    void shouldThrowNullPointerException_whenBirthDateIsNull() {
        assertThrows(NullPointerException.class, () -> userUseCase.validateAge(null));
    }

    @Test
    void shouldReturnTrue_whenEmailIsValid() {
        assertTrue(userUseCase.validateEmail("user@example.com"));
    }

    @Test
    void shouldReturnFalse_whenEmailIsInvalid() {
        assertFalse(userUseCase.validateEmail("wrong-email"));
    }

    @Test
    void shouldReturnFalse_whenEmailIsEmpty() {
        assertFalse(userUseCase.validateEmail(""));
    }

    @Test
    void shouldEncryptPassword_whenPasswordIsValid() {
        String password = "password123";
        String encryptedPassword = userUseCase.encryptPassword(password);

        assertNotNull(encryptedPassword);
        assertNotEquals(password, encryptedPassword);
    }

    @Test
    void shouldReturnEncryptedPassword_whenPasswordIsEmpty() {
        String encryptedPassword = userUseCase.encryptPassword("");
        assertNotNull(encryptedPassword);
    }

    @Test
    void shouldReturnTrue_whenPhoneIsValid() {
        assertTrue(userUseCase.validatePhone("1234567890"));
    }

    @Test
    void shouldReturnFalse_whenPhoneIsInvalid() {
        assertFalse(userUseCase.validatePhone("invalid-phone"));
    }

    @Test
    void shouldReturnFalse_whenPhoneIsTooLong() {
        assertFalse(userUseCase.validatePhone("12345678901234"));
    }

    @Test
    void shouldReturnFalse_whenPhoneIsEmpty() {
        assertFalse(userUseCase.validatePhone(""));
    }

    private User createUser() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPhone("1234567890");
        user.setDocument("123456");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setPassword("password");
        Role role = new Role();
        role.setName("USER");
        user.setRole(role);
        return user;
    }

}