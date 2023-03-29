package com.spring.rest.api.entity.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer rentalPeriod;
}