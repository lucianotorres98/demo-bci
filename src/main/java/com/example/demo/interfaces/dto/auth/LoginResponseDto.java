package com.example.demo.interfaces.dto.auth;

import com.example.demo.domain.entities.UserEntity;
import com.example.demo.interfaces.dto.phone.PhoneDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginResponseDto {
    long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime lastLogin;
    String token;
    boolean isActive;
    String name;
    String email;
    String password;
    List<PhoneDto> phones;

    public static LoginResponseDto fromEntity(UserEntity user) {
        return LoginResponseDto.builder()
                .id(user.getId())
                .created(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(PhoneDto.fromEntityList(user.getPhones()))
                .build();
    }

}