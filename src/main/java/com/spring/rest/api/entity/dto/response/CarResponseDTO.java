package com.spring.rest.api.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_BROKEN;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_BROKEN_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_IMAGE_LINK;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_IMAGE_LINK_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_MODEL;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRODUCER;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_RELEASE_DATE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_RELEASE_DATE_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_UUID;


/**
 * This class presents a DTO, which is available via CarController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_UUID, description = CAR_UUID)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_PRODUCER, description = CAR_PRODUCER)
    private String producer;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_MODEL, description = CAR_MODEL)
    private String model;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_RELEASE_DATE, description = CAR_RELEASE_DATE_DESCRIPTION)
    private LocalDate releaseDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_PRICE_PER_DAY, description = CAR_PRICE_PER_DAY_DESCRIPTION)
    private Double pricePerDay;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_EMPLOYMENT_STATUS, description = CAR_EMPLOYMENT_STATUS_DESCRIPTION)
    private boolean busy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_DAMAGE_STATUS, description = CAR_DAMAGE_STATUS_DESCRIPTION)
    private String damageStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_IMAGE_LINK, description = CAR_IMAGE_LINK_DESCRIPTION)
    private String imageLink;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = CAR_BROKEN, description = CAR_BROKEN_DESCRIPTION)
    private boolean broken;
}
