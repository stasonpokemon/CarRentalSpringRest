package com.spring.rest.api.entity;

import com.spring.rest.api.util.PostgreSQLEnumType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@Data
// This annotation for connect jpa with postgres enum
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
public class Order extends BaseEntity {

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Type(type = "enum_type")
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "rental_period")
    private Integer rentalPeriod;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "refund_id")
    private Refund refund;


}