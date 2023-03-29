package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.dto.PassportDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.spring.rest.api.util.swagger.OpenApiConstants.*;
import static com.spring.rest.api.util.swagger.OpenApiConstants.USER_EMAIL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

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
    private PassportDTO passport;
}