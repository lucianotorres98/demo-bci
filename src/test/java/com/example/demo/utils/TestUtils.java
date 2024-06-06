package com.example.demo.utils;

import com.example.demo.domain.entities.UserEntity;
import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.LoginResponseDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;
import com.example.demo.interfaces.dto.phone.PhoneDto;

import java.time.LocalDateTime;
import java.util.List;

public class TestUtils {

public static String EMAIL = "luciano.torres@globallogic.com";
public static String PASSWORD = "Password12";


    public static RegisterDto createRegisterDto() {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber(1234567);
        phoneDto.setCityCode(1);
        phoneDto.setCountryCode("57");

        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail(EMAIL);
        registerDto.setPassword(PASSWORD);
        registerDto.setPhones(List.of(phoneDto));

        return registerDto;
    }


    public static LoginDto createLoginDto() {
        return new LoginDto().builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

    public static UserEntity createUserEntity() {
        return UserEntity.builder()
            .id(1)
            .createdAt(LocalDateTime.now())
            .lastLogin(LocalDateTime.now())
            .token("token")
            .isActive(true)
            .name("Luciano Torres")
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }
    public static LoginResponseDto createLoginResponseDto() {
        return new LoginResponseDto().builder()
            .id(1)
            .created(LocalDateTime.now())
            .lastLogin(LocalDateTime.now())
            .token("token")
            .isActive(true)
            .name("Luciano Torres")
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

}
