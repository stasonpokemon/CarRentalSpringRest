package com.spring.rest.api.entity;

public enum OrderStatus {
    UNDER_CONSIDERATION("НА РАССМОТРЕНИИ"),
    REFUSAL("ОТКАЗАНО"),
    CONFIRMED("ПРИНЯТО");

    String abbreviation;

    OrderStatus(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }
}