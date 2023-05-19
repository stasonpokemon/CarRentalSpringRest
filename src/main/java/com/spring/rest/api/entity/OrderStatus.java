package com.spring.rest.api.entity;

import java.util.stream.Stream;

/**
 * OrderStatus enum class for Order entity.
 */
public enum OrderStatus {
    UNDER_CONSIDERATION(0),
    REFUSAL(1),
    CONFIRMED(2);

    final int number;

    OrderStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public static OrderStatus of(int number) {
        return Stream.of(OrderStatus.values())
                .filter(p -> p.getNumber() == number)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
