package com.example.demo.app.service.auth;

import com.example.demo.interfaces.dto.auth.LoginDto;
import com.example.demo.interfaces.dto.auth.LoginResponseDto;
import com.example.demo.interfaces.dto.auth.RegisterDto;

public interface AuthService {

    LoginResponseDto signUp(RegisterDto req);

    LoginResponseDto login(LoginDto req);

}
