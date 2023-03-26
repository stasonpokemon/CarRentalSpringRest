package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.dto.PassportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PassportDTO passport;
}