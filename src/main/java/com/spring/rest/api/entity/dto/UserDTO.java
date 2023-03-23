package com.spring.rest.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @Length(min = 6, message = "Username must be more than 6 symbols")
    private String username;

    @Length(min = 6, message = "Password must be more than 6 symbols")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Please fill the email")
    @Email(message = "Please fill the correct email")
    private String email;

    private PassportDTO passport;
}