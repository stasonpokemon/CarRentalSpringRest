package com.spring.rest.api.entity.dto;


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


    private Car car;

    private User user;

    private double price;

    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    @NotNull(message = "Please fill the rental period")
    @Min(value = 1, message = "Rental period should be greater than 1")
    private Integer rentalPeriod;

    private Refund refund;

}