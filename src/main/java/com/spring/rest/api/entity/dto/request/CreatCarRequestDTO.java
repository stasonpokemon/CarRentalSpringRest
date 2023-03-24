package com.spring.rest.api.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatCarRequestDTO {

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

    @NotBlank(message = "Please fill the image link")
    private String imageLink;
}
