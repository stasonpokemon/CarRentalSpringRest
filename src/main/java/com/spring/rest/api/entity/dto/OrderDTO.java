package com.spring.rest.api.entity.dto;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.OrderStatus;
import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer rentalPeriod;

    private Refund refund;

}