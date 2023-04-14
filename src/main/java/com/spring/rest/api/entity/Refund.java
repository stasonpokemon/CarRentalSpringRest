package com.spring.rest.api.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This class presents an refund's entity, which will be stored in the database.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "refunds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Order order;
}
