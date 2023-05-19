package com.spring.rest.api.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_DAMAGE_STATUS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_EMPLOYMENT_STATUS_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_IMAGE_LINK;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_IMAGE_LINK_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_MODEL;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_MODEL_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRICE_PER_DAY_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRODUCER;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_PRODUCER_DESCRIPTION;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_RELEASE_DATE;
import static com.spring.rest.api.util.swagger.OpenApiConstants.CAR_RELEASE_DATE_DESCRIPTION;

/**
 * This class presents a DTO, which is available via CarController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCarRequestDTO {

    @NotBlank(message = "Please fill the producer")
    @Length(max = 255, message = "Producer too long. Max length is 2048")
    @Schema(example = CAR_PRODUCER, description = CAR_PRODUCER_DESCRIPTION)
    private String producer;

    @NotBlank(message = "Please fill the model")
    @Length(max = 255, message = "Model too long. Max length is 2048")
    @Schema(example = CAR_MODEL, description = CAR_MODEL_DESCRIPTION)
    private String model;

    @NotNull(message = "Please fill the release date")
    @Schema(example = CAR_RELEASE_DATE, description = CAR_RELEASE_DATE_DESCRIPTION)
    private LocalDate releaseDate;

    @NotNull(message = "Please fill the price per day")
    @Min(value = 0, message = "Price per day can't be less than 0")
    @Schema(example = CAR_PRICE_PER_DAY, description = CAR_PRICE_PER_DAY_DESCRIPTION)
    private Double pricePerDay;

    @NotNull(message = "Employment status can't be null")
    @Schema(example = CAR_EMPLOYMENT_STATUS, description = CAR_EMPLOYMENT_STATUS_DESCRIPTION)
    private boolean busy;

    @Length(max = 255, message = "Damage status too long. Max length is 1000")
    @NotBlank(message = "Please fill the damage status")
    @Schema(example = CAR_DAMAGE_STATUS, description = CAR_DAMAGE_STATUS_DESCRIPTION)
    private String damageStatus;

    @NotBlank(message = "Please fill the image link")
    @Schema(example = CAR_IMAGE_LINK, description = CAR_IMAGE_LINK_DESCRIPTION)
    private String imageLink;
}

