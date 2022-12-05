package com.spring.rest.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportDTO {

    @NotBlank(message = "Please fill the name")
    private String name;

    @NotBlank(message = "Please fill the surname")
    private String surname;

    @NotBlank(message = "Please fill the patronymic")
    private String patronymic;

    @NotNull(message = "Please fill the birthday")
    private Date birthday;

    @NotBlank(message = "Please fill the address")
    private String address;
}