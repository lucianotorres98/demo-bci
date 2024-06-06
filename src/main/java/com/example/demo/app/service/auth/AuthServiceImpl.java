package com.example.demo.app.service.auth;

import com.example.demo.app.exception.DatabaseException;
import com.example.demo.app.exception.InvalidEmailException;
import com.example.demo.app.exception.InvalidPasswordException;
import com.example.demo.app.exception.UserAlreadyExistsException;
import com.example.demo.domain.entities.PhoneEntity;
import com.example.demo.domain.entities.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.infra.constant.JWTConstant;
import com.example.demo.infra.utils.Helpers;
import com.example.demo.infra.utils.Jwt;
import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.LoginResponseDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final Jwt jwt;

    private final Helpers helpers;

    @Override
    public LoginResponseDto signUp(RegisterDto input) {
        logger.info("Signing up user with email: {}", input.getEmail());

        Optional<UserEntity> userOptional = this.userRepository.findByEmail(Optional.ofNullable(input.getEmail()).orElseThrow());

        if (userOptional.isPresent()) {
            logger.error("Users already exists: {}", input.getEmail());
            throw new UserAlreadyExistsException("User already exists");
        }

        logger.info("Validating email and password");
        validateEmailAndPassword(input.getEmail(), input.getPassword());

        logger.info("Creating user with email: {}", input.getEmail());
        UserEntity user = UserEntity.builder()
                .uuid(String.valueOf(UUID.randomUUID()))
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .isActive(true)
                .build();

        // add token and phones to user
        user.setToken(jwt.generateToken(user));
        Optional.ofNullable(input.getPhones()).ifPresent(phones -> user.setPhones(PhoneEntity.fromDtoList(phones, user)));

        UserEntity savedUser;
        try {
            savedUser = userRepository.save(user);
            logger.info("User created with email: {}", input.getEmail());
        } catch (DataAccessException ex) {
            logger.error("Error occurred while saving user to the database: {}", ex.getMessage());
            throw new DatabaseException("Error occurred while saving user to the database", ex);
        }

        logger.info("User saved with email: {}", input.getEmail());
        return LoginResponseDto.fromEntity(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginDto input) {
        logger.info("Logging in user with email: {}", input.getEmail());

        UserEntity user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return LoginResponseDto.fromEntity(user);
    }

    private void validateEmailAndPassword(String email, String password) {
        if (!helpers.patternMatches(email, JWTConstant.EMAIL_REGEX)) {
            logger.error("Invalid email: {}", email);
            throw new InvalidEmailException("Invalid email ");
        }
        if (!helpers.patternMatches(password, JWTConstant.PASSWORD_REGEX)) {
            logger.error("Invalid password: {}", password);
            throw new InvalidPasswordException("Invalid password");
        }
    }
}