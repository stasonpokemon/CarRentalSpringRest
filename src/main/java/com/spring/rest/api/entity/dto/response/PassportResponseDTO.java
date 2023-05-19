package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_ADDRESS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_ADDRESS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_BIRTHDAY;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_BIRTHDAY_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_NAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_PATRONYMIC;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_PATRONYMIC_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_SURNAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_SURNAME_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID_DESCRIPTION;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = PASSPORT_UUID, description = PASSPORT_UUID)
    private UUID id;
    @Length(max = 255, message = "Name too long. Max length is 255")
    @NotBlank(message = "Please fill the name")
    @Schema(example = PASSPORT_NAME, description = PASSPORT_NAME)
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

    @JsonProperty(namespace = "userId", access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_UUID, description = USER_UUID_DESCRIPTION)
    private UUID userId;
}