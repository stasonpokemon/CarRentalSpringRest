package com.spring.rest.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPassportDTO {

    private String name;

    private String surname;

    private String patronymic;

    private Date birthday;

    private String address;
}