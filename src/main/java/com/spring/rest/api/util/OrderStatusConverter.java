package com.spring.rest.api.util;

import com.spring.rest.api.entity.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
