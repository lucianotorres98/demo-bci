package com.example.demo.service;

import com.example.demo.app.exception.DatabaseException;
import com.example.demo.app.exception.InvalidEmailException;
import com.example.demo.app.exception.InvalidPasswordException;
import com.example.demo.app.exception.UserAlreadyExistsException;
import com.example.demo.app.service.auth.AuthServiceImpl;
import com.example.demo.domain.entities.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.infra.utils.Helpers;
import com.example.demo.infra.utils.Jwt;
import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Jwt jwt;

    @Mock
    private Helpers helpers;

    @Test
    @DisplayName("Should return LoginResponseDto when valid RegisterDto is provided and user does not exist")
    void signUpWithValidInputAndUserDoesNotExist() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        UserEntity userEntity = TestUtils.createUserEntity(); // Ensure UserEntity is fully initialized
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwt.generateToken(any())).thenReturn("token");
        when(userRepository.save(any())).thenReturn(userEntity); // Ensure the saved UserEntity is returned

        when(helpers.patternMatches(anyString(), anyString())).thenReturn(true);
        when(helpers.patternMatches(anyString(), anyString())).thenReturn(true, true);

        assertDoesNotThrow(() -> authService.signUp(registerDto));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when user already exists")
    void signUpUserAlreadyExists() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        UserEntity userEntity = new UserEntity();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        assertThrows(UserAlreadyExistsException.class, () -> authService.signUp(registerDto));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email is invalid")
    void signUpWithInvalidEmail() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        registerDto.setEmail("invalidEmail");
        when(helpers.patternMatches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidEmailException.class, () -> authService.signUp(registerDto));
    }

    @Test
    @DisplayName("Should throw InvalidPasswordException when password is invalid")
    void signUpWithInvalidPassword() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        registerDto.setPassword("invalidPassword");
        when(helpers.patternMatches(anyString(), anyString())).thenReturn(true, false);

        assertThrows(InvalidPasswordException.class, () -> authService.signUp(registerDto));
    }

    @Test
    @DisplayName("Should throw DatabaseException when there is a database access error")
    void signUpWithDatabaseAccessException() {
        RegisterDto registerDto = TestUtils.createRegisterDto();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwt.generateToken(any())).thenReturn("token");
        when(helpers.patternMatches(anyString(), anyString())).thenReturn(true);
        when(userRepository.save(any())).thenThrow(new DataAccessException("Database access error") {});

        assertThrows(DatabaseException.class, () -> authService.signUp(registerDto));
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user does not exist")
    void loginWithNonExistentUser() {
        LoginDto loginDto = TestUtils.createLoginDto();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginDto));
    }

    @Test
    @DisplayName("Should return LoginResponseDto when valid LoginDto is provided and user exists")
    void loginWithValidInputAndUserExists() {
        LoginDto loginDto = TestUtils.createLoginDto();
        UserEntity userEntity = TestUtils.createUserEntity();
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(userEntity));
        when(authenticationManager.authenticate(any())).thenReturn(null);

        assertEquals(TestUtils.EMAIL, authService.login(loginDto).getEmail());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when invalid email is provided")
    void loginWithInvalidEmail() {
        LoginDto loginDto = TestUtils.createLoginDto();
        loginDto.setEmail("invalidEmail");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginDto));
    }

    @Test
    @DisplayName("Should throw BadCredentialsException when invalid password is provided")
    void loginWithInvalidPassword() {
        LoginDto loginDto = TestUtils.createLoginDto();
        UserEntity userEntity = TestUtils.createUserEntity();
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(userEntity));
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(loginDto));
    }

}