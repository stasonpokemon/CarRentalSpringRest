package com.spring.rest.api.entity;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
@Data
public class Car extends BaseEntity {

    @Column(name = "producer")
    private String producer;

    @Column(name = "model")
    private String model;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "price_per_day")
    private Double pricePerDay;

    @Column(name = "employment_status")
    private boolean employmentStatus;

    @Column(name = "damage_status")
    private String damageStatus;

    @Column(name = "img_link")
    private String imageLink;

    @Column(name = "is_deleted")
    private boolean deleted;


}