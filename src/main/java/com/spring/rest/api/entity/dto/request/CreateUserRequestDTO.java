package com.spring.rest.api.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.spring.rest.api.util.swagger.OpenApiConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequestDTO {

    @Length(min = 6, message = "Username must be more than 6 symbols")
    @Schema(example = USER_USERNAME, description = USER_USERNAME)
    private String username;

    @Length(min = 6, message = "Password must be more than 6 symbols")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(example = USER_PASSWORD, description = USER_PASSWORD)
    private String password;

    @NotBlank(message = "Please fill the email")
    @Email(message = "Please fill the correct email")
    @Schema(example = USER_EMAIL, description = USER_EMAIL)
    private String email;
}
