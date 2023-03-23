package com.spring.rest.api.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {

    @NotNull(message = "Please fill the rental period")
    @Min(value = 1, message = "Rental period should be greater than 1")
    private Integer rentalPeriod;
}
