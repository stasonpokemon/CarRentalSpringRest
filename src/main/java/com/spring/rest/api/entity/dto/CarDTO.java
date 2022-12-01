package com.spring.rest.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private String producer;
    private String model;
    private Date releaseDate;
    private Double pricePerDay;
    private boolean employmentStatus;
    private String damageStatus;
    private String imageLink;
}
