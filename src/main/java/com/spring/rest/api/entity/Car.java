package com.spring.rest.api.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    @Column(name = "is_broken")
    private boolean broken;
}