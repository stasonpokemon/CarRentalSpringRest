package com.spring.rest.api.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import static com.spring.rest.api.util.swagger.OpenApiConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportDTO {

    @Length(max = 255, message = "Name too long. Max length is 255")
    @NotBlank(message = "Please fill the name")
    @Schema(example = PASSPORT_NAME, description = PASSPORT_NAME)
    private String name;

    @Length(max = 255, message = "Surname too long. Max length is 255")
    @NotBlank(message = "Please fill the surname")
    @Schema(example = PASSPORT_SURNAME, description = PASSPORT_SURNAME)
    private String surname;

    @Length(max = 255, message = "Patronymic too long. Max length is 255")
    @NotBlank(message = "Please fill the patronymic")
    @Schema(example = PASSPORT_PATRONYMIC, description = PASSPORT_PATRONYMIC)
    private String patronymic;

    @NotNull(message = "Please fill the birthday")
    @Schema(example = PASSPORT_BIRTHDAY, description = PASSPORT_BIRTHDAY)
    private Date birthday;

    @Length(max = 500, message = "Address too long. Max length is 500")
    @NotBlank(message = "Please fill the address")
    @Schema(example = PASSPORT_ADDRESS, description = PASSPORT_ADDRESS)
    private String address;
}