package com.spring.rest.api.entity;


import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car extends BaseEntity {

    @Column(name = "producer")
    private String producer;

    @Column(name = "model")
    private String model;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "price_per_day")
    private Double pricePerDay;

    @Column(name = "is_busy")
    private boolean busy;

    @Column(name = "damage_status")
    private String damageStatus;

    @Column(name = "img_link")
    private String imageLink;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column(name = "is_broken")
    private boolean broken;
}