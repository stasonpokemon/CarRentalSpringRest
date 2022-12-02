package com.spring.rest.api.entity;

public enum OrderStatus {
    UNDER_CONSIDERATION("Under consideration"),
    REFUSAL("Refusal"),
    CONFIRMED("Confirmed");

    String abbreviation;

    OrderStatus(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}