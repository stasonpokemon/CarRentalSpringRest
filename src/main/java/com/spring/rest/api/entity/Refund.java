package com.spring.rest.api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "refunds")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Refund extends BaseEntity {

    @Column(name = "damage_status")
    private boolean damaged;

    @Column(name = "damage_description")
    private String damageDescription;

    @Column(name = "price")
    private double price;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    @OneToOne(mappedBy = "refund", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Order order;
}