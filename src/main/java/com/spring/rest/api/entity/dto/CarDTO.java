package com.spring.rest.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    @NotBlank(message = "Please fill the producer")
    @Length(max = 255, message = "Producer too long. Max length is 2048")
    private String producer;

    @NotBlank(message = "Please fill the model")
    @Length(max = 255, message = "Model too long. Max length is 2048")
    private String model;

    @NotNull(message = "Please fill the release date")
    private LocalDate releaseDate;

    @NotNull(message = "Please fill the price per day")
    @Min(value = 0, message = "Price per day can't be less than 0")
    private Double pricePerDay;

    @NotNull(message = "Employment status can't be null")
    private boolean employmentStatus;

    @Length(max = 255, message = "Damage status too long. Max length is 1000")
    @NotBlank(message = "Please fill the damage status")
    private String damageStatus;

    @NotBlank(message = "Please fill the image link")
    private String imageLink;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean broken;


}
