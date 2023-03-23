package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderDTO orderToOrderDTO(Order order);

    Order orderDTOToOrder(OrderDTO orderDTO);

}
