package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String producer;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String model;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate releaseDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double pricePerDay;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean employmentStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String damageStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageLink;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean broken;
}