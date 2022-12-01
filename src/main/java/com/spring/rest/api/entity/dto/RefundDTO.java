package com.spring.rest.api.entity.dto;

import com.spring.rest.api.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {


    private Order order;

    private boolean damageStatus;

    private String damageDescription;

    private double price;

    private LocalDateTime refundDate;

}