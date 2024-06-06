package com.example.demo.interfaces.api;

import com.example.demo.app.service.auth.AuthService;
import com.example.demo.infra.constant.ApiConstant;
import com.example.demo.infra.utils.Helpers;
import com.example.demo.infra.utils.Jwt;
import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.LoginResponseDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping(ApiConstant.AUTH_ENDPOINT)
public class AuthApi {

    private final Logger logger = LoggerFactory.getLogger(AuthApi.class);

    private final Jwt jwtService;

    private final AuthService authenticationService;


    public AuthApi(Jwt jwtService, AuthService authenticationService, Helpers helpers) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> signUp(@RequestBody RegisterDto registerUserDto) {
        logger.info("Registering user with email: {}", registerUserDto.getEmail());

        LoginResponseDto registeredUser = authenticationService.signUp(registerUserDto);

        logger.info("Registered user with email: {}", registerUserDto.getEmail());

        return ResponseEntity.created(URI.create("")).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginUserDto) {
        logger.info("Authenticating user with email: {}", loginUserDto.getEmail());

         LoginResponseDto authenticatedUser = authenticationService.login(loginUserDto);

        logger.info("Authenticated user with email: {}", loginUserDto.getEmail());

        return ResponseEntity.ok(authenticatedUser);
    }
}