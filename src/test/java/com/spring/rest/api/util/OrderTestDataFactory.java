package com.spring.rest.api.util;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.OrderStatus;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.mapper.OrderMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderTestDataFactory {

    private final static OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    public static Order buildNewOrder(User user) {
        return Order.builder()
                .orderDate(LocalDateTime.now())
                .user(UserTestDataFactory.buildUserWithPassport())
                .car(CarTestDataFactory.buildCar())
                .price(100)
                .rentalPeriod(2)
                .orderStatus(OrderStatus.UNDER_CONSIDERATION)
                .user(user).build();
    }

    public static OrderResponseDTO buildOrderToOrderResponseDTO(Order order){
        return orderMapper.orderToOrderDTO(order);
    }

}
