package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_ACTIVE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_EMAIL;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_PASSWORD;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_USERNAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID;


/**
 * This class presents a DTO, which is available via UserController and RegistrationController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_UUID, description = USER_UUID)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_USERNAME, description = USER_USERNAME)
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_PASSWORD, description = USER_PASSWORD)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_EMAIL, description = USER_EMAIL)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_ACTIVE, description = USER_ACTIVE)
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = PASSPORT_UUID, description = PASSPORT_UUID)
    private UUID passportId;
}
