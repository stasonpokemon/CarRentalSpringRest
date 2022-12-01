package com.spring.rest.api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
@Data
public class Car extends BaseEntity {
    
    @NotBlank(message = "Please fill the producer")
    @Length(max = 2048, message = "Producer too long. Max length is 2048")
    @Column(name = "producer")
    private String producer;

    @NotBlank(message = "Please fill the model")
    @Length(max = 2048, message = "Model too long. Max length is 2048")
    @Column(name = "model")
    private String model;

    @NotBlank(message = "Please fill the release date")
    @Column(name = "release_date")
    private Date releaseDate;

    @NotNull(message = "Please fill the price per day")
    @Min(value = 0, message = "Price per day can't be less than 0")
    @Column(name = "price_per_day")
    private Double pricePerDay;

    @NotNull(message = "Employment status can't be null")
    @Column(name = "employment_status")
    private boolean employmentStatus;

    @NotBlank(message = "Please fill the damage status")
    @Column(name = "damage_status")
    private String damageStatus;

    @NotBlank(message = "Please fill the image link")
    @Column(name = "img_link")
    private String imageLink;

    @Column(name = "is_deleted")
    private boolean deleted;


}