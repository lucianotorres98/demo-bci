package com.example.demo.interfaces.dto.phone;

import com.example.demo.domain.entities.PhoneEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PhoneDto {

    long number;

    Integer cityCode;

    String countryCode;

    public static PhoneDto fromEntity(PhoneEntity phone) {
        return PhoneDto.builder()
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .build();
    }

    public static List<PhoneDto> fromEntityList(List<PhoneEntity> phoneEntities) {
        return phoneEntities.stream()
                .map(PhoneDto::fromEntity)
                .collect(Collectors.toList());
    }

}