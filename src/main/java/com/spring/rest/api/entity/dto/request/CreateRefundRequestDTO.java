package com.spring.rest.api.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRefundRequestDTO {

    @NotNull(message = "isDamaged field must be filled")
    private boolean damaged;

    private String damageDescription;

    @Min(value = 0, message = "Price must be 0 or greater than 0")
    private double price;

}
