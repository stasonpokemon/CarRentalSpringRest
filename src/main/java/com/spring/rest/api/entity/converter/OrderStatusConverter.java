package com.spring.rest.api.entity.converter;

import com.spring.rest.api.entity.OrderStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * OrderStatusConverter class for convert OrderStatus enum to integer for database.
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus == null ? -1 : orderStatus.getNumber();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer integer) {
        return OrderStatus.of(integer);
    }
}
