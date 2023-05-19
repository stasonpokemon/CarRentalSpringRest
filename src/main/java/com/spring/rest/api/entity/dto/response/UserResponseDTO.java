package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.PASSPORT_UUID_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_ACTIVE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_ACTIVE_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_EMAIL;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_EMAIL_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_PASSWORD;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_PASSWORD_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_USERNAME;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_USERNAME_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_UUID_DESCRIPTION;


/**
 * This class presents a DTO, which is available via UserController and RegistrationController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_UUID, description = USER_UUID_DESCRIPTION)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_USERNAME, description = USER_USERNAME_DESCRIPTION)
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_PASSWORD, description = USER_PASSWORD_DESCRIPTION)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_EMAIL, description = USER_EMAIL_DESCRIPTION)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = USER_ACTIVE, description = USER_ACTIVE_DESCRIPTION)
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = PASSPORT_UUID, description = PASSPORT_UUID_DESCRIPTION)
    private UUID passportId;
}
