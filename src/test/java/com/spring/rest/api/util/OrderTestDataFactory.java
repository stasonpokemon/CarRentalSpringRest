package com.spring.rest.api.util;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.OrderStatus;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.mapper.OrderMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderTestDataFactory {

    private final static OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    public static Order buildOrder(User user) {
        return Order.builder()
                .orderDate(LocalDateTime.now())
                .user(UserTestDataFactory.buildUserWithPassport())
                .car(CarTestDataFactory.buildCar())
                .price(100)
                .rentalPeriod(2)
                .orderStatus(OrderStatus.UNDER_CONSIDERATION)
                .user(user).build();
    }

    public static Order buildOrder(CreateOrderRequestDTO createOrderRequestDTO, User user, Car car) {
        return Order.builder()
                .orderDate(LocalDateTime.now())
                .user(UserTestDataFactory.buildUserWithPassport())
                .car(car)
                .price(100)
                .rentalPeriod(createOrderRequestDTO.getRentalPeriod())
                .orderStatus(OrderStatus.UNDER_CONSIDERATION)
                .user(user).build();
    }

    public static Order buildAcceptedOrder(Order order) {
        return Order.builder()
                .orderDate(order.getOrderDate())
                .user(order.getUser())
                .car(order.getCar())
                .price(order.getPrice())
                .rentalPeriod(order.getRentalPeriod())
                .orderStatus(OrderStatus.CONFIRMED).build();
    }

    public static Order buildCanceledOrder(Order order) {
        return Order.builder()
                .orderDate(order.getOrderDate())
                .user(order.getUser())
                .car(order.getCar())
                .price(order.getPrice())
                .rentalPeriod(order.getRentalPeriod())
                .orderStatus(OrderStatus.REFUSAL).build();
    }

    public static CreateOrderRequestDTO buildCreateOrderRequestDTO(UUID carId, UUID userId) {
        return CreateOrderRequestDTO.builder()
                .carId(carId.toString())
                .userId(userId.toString())
                .rentalPeriod(3).build();
    }


    public static OrderResponseDTO buildOrderToOrderResponseDTO(Order order) {
        return orderMapper.orderToOrderResponseDTO(order);
    }

    public static Page<OrderResponseDTO> buildOrderResponseDTOPageFromUser(User user) {
        List<OrderResponseDTO> orderResponseDTOS = user.getOrders().stream()
                .map(orderMapper::orderToOrderResponseDTO).toList();

        return new PageImpl<>(orderResponseDTOS);
    }
}
