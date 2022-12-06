package com.spring.rest.api.entity.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.OrderStatus;
import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CarDTO car;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RefundDTO refund;

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