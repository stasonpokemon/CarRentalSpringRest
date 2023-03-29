package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.spring.rest.api.util.swagger.OpenApiConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_PRODUCER, description = CAR_PRODUCER)
    private String producer;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_MODEL, description = CAR_MODEL)
    private String model;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_RELEASE_DATE, description = CAR_RELEASE_DATE)
    private LocalDate releaseDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_PRICE_PER_DAY, description = CAR_PRICE_PER_DAY)
    private Double pricePerDay;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_EMPLOYMENT_STATUS, description = CAR_EMPLOYMENT_STATUS)
    private boolean employmentStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_DAMAGE_STATUS, description = CAR_DAMAGE_STATUS)
    private String damageStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_IMAGE_LINK, description = CAR_IMAGE_LINK)
    private String imageLink;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_BROKEN, description = CAR_BROKEN)
    private boolean broken;
}