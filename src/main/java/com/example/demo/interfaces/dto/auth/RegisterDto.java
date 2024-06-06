package com.example.demo.interfaces.dto.auth;

import com.example.demo.interfaces.dto.phone.PhoneDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegisterDto {
    private String name;

    private String email;

    private String password;

    private List<PhoneDto> phones;

}