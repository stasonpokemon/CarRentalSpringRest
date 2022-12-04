package com.spring.rest.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPassportDTO {

    @NotBlank(message = "Please fill the name")
    private String name;

    @NotBlank(message = "Please fill the surname")
    private String surname;

    @NotBlank(message = "Please fill the patronymic")
    private String patronymic;

    @NotBlank(message = "Please fill the birthday")
    private Date birthday;

    @NotBlank(message = "Please fill the address")
    private String address;
}