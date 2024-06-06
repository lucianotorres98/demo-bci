package com.example.demo.service;

import com.example.demo.app.service.auth.AuthServiceImpl;
import com.example.demo.domain.entities.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.LoginResponseDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should throw exception when user already exists")
    void signUpUserAlreadyExists() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        UserEntity userEntity = new UserEntity();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        assertThrows(RuntimeException.class, () -> authService.signUp(registerDto));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @DisplayName("Should return LoginResponseDto when valid RegisterDto is provided")
    void signUpWithValidInput() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> authService.signUp(registerDto));
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void signUpWithInvalidEmail() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        registerDto.setEmail("invalidEmail");

        assertThrows(RuntimeException.class, () -> authService.signUp(registerDto));
    }

    @Test
    @DisplayName("Should throw exception when password is invalid")
    void signUpWithInvalidPassword() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        registerDto.setPassword("invalidPassword");

        assertThrows(RuntimeException.class, () -> authService.signUp(registerDto));
    }

    @Test
    @DisplayName("Should throw RuntimeException when database error occurs")
    void loginWithDatabaseError() {
        LoginDto loginDto = TestUtils.createLoginDto();
        when(userRepository.findByEmail(anyString())).thenThrow(new RuntimeException("Database error: Error while fetching user from database"));

        assertThrows(RuntimeException.class, () -> authService.login(loginDto));
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void loginWithInvalidEmail() {
        LoginDto loginDto = TestUtils.createLoginDto();
        loginDto.setEmail("invalidEmail");

        assertThrows(RuntimeException.class, () -> authService.login(loginDto));
    }

    @Test
    @DisplayName("Should throw exception when password is invalid")
    void loginWithInvalidPassword() {
        LoginDto loginDto = TestUtils.createLoginDto();
        loginDto.setPassword("invalidPassword");

        assertThrows(RuntimeException.class, () -> authService.login(loginDto));
    }

}