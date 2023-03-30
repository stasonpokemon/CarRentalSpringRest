package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    @Mapping(target = "carId", source = "car.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "refundId", source = "refund.id")
    OrderResponseDTO orderToOrderDTO(Order order);

    Order createOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);
}
