package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderResponseDTO orderToOrderDTO(Order order);

    Order createOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);

}
