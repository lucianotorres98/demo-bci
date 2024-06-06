package com.example.demo.domain.entities;

import com.example.demo.interfaces.dto.phone.PhoneDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "phone")
public class PhoneEntity {


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number")
    private long number;

    @NotNull
    @Column(name = "city_code")
    private Integer cityCode;

    @NotNull
    @Column(name = "country_code")
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity user;

    public static List<PhoneEntity> fromDtoList(List<PhoneDto> phoneDtos, UserEntity user) {
        List<PhoneEntity> phoneEntities = new ArrayList<>();
        for (PhoneDto phoneDto : phoneDtos) {
            PhoneEntity phoneEntity = new PhoneEntity();
            phoneEntity.setNumber(phoneDto.getNumber());
            phoneEntity.setCityCode(phoneDto.getCityCode());
            phoneEntity.setCountryCode(phoneDto.getCountryCode());
            phoneEntity.setUser(user);
            phoneEntities.add(phoneEntity);
        }
        return phoneEntities;
    }
}