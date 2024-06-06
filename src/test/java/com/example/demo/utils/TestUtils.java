package com.example.demo.utils;

import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;

public class TestUtils {

public static String EMAIL = "aaaaaaa@undominio.algo";
public static String PASSWORD = "IAmavalidpassword12";


public static RegisterDto createRegisterDto() {
    return new RegisterDto().builder()
            .name("John Doe")
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

    public static LoginDto createLoginDto() {
        return new LoginDto().builder()
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

}
