package com.spring.rest.api.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


import java.util.Date;

import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_ADDRESS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_ADDRESS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_BIRTHDAY;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_BIRTHDAY_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_NAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_NAME_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_PATRONYMIC;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_PATRONYMIC_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_SURNAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_SURNAME_DESCRIPTION;


/**
 * This class presents a DTO, which is available via UserController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportRequestDTO {

    @Length(max = 255, message = "Name too long. Max length is 255")
    @NotBlank(message = "Please fill the name")
    @Schema(example = PASSPORT_NAME, description = PASSPORT_NAME_DESCRIPTION)
    private String name;

    @Length(max = 255, message = "Surname too long. Max length is 255")
    @NotBlank(message = "Please fill the surname")
    @Schema(example = PASSPORT_SURNAME, description = PASSPORT_SURNAME_DESCRIPTION)
    private String surname;

    @Length(max = 255, message = "Patronymic too long. Max length is 255")
    @NotBlank(message = "Please fill the patronymic")
    @Schema(example = PASSPORT_PATRONYMIC, description = PASSPORT_PATRONYMIC_DESCRIPTION)
    private String patronymic;

    @NotNull(message = "Please fill the birthday")
    @Schema(example = PASSPORT_BIRTHDAY, description = PASSPORT_BIRTHDAY_DESCRIPTION)
    private Date birthday;

    @Length(max = 500, message = "Address too long. Max length is 500")
    @NotBlank(message = "Please fill the address")
    @Schema(example = PASSPORT_ADDRESS, description = PASSPORT_ADDRESS_DESCRIPTION)
    private String address;
}
