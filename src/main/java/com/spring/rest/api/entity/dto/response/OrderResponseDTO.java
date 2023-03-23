package com.spring.rest.api.entity.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CarResponseDTO car;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserResponseDTO user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RefundResponseDTO refund;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double price;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus orderStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime orderDate;

    @NotNull(message = "Please fill the rental period")
    @Min(value = 1, message = "Rental period should be greater than 1")
    private Integer rentalPeriod;
}