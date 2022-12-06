package com.spring.rest.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {

    private boolean damageStatus;

    private String damageDescription;

    private double price;

    private LocalDateTime refundDate;

}