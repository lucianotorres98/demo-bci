package com.example.demo.api;

import com.example.demo.app.service.auth.AuthService;
import com.example.demo.infra.utils.Jwt;
import com.example.demo.interfaces.api.AuthApi;
import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.LoginResponseDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
public class AuthApiTest {

    @InjectMocks
    private AuthApi authApi;

    @Mock
    private AuthService authService;

    @Test
    @DisplayName("Should register user successfully")
    public void signUpSuccess() {
        RegisterDto registerUserDto = new RegisterDto();
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        when(authService.signUp(registerUserDto)).thenReturn(loginResponseDto);

        ResponseEntity<LoginResponseDto> response = authApi.signUp(registerUserDto);

        assertEquals(ResponseEntity.created(URI.create("")).body(loginResponseDto), response);
        verify(authService, times(1)).signUp(registerUserDto);
    }

    @Test
    @DisplayName("Should login user successfully")
    public void loginSuccess() {
        LoginDto loginUserDto = new LoginDto();
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        when(authService.login(loginUserDto)).thenReturn(loginResponseDto);

        ResponseEntity<LoginResponseDto> response = authApi.login(loginUserDto);

        assertEquals(ResponseEntity.ok(loginResponseDto), response);
        verify(authService, times(1)).login(loginUserDto);
    }
}