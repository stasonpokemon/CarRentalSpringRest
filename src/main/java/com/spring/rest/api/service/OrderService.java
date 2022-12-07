package com.spring.rest.api.service;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.OrderDTO;
import com.spring.rest.api.entity.dto.RefundDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> findAll(String[] sort);

    ResponseEntity<?> findById(Long orderId);

    ResponseEntity<?> createOrder(Order order, Long userId, Long carId);

    ResponseEntity<?> acceptOrder(Long orderId);

    ResponseEntity<?> cancelOrder(Long orderId);

    ResponseEntity<?> createOrdersRefund(Long orderId, RefundDTO refundDTO);

    ResponseEntity<?> findOrdersRefund(Long orderId);

    ResponseEntity<?> findOrdersByUserId(Long userId);



//    ResponseEntity<?> findAll(String[] sort);
//
//    ResponseEntity<?> findAllByUserId(Long id, String[] sort);
//
//    ResponseEntity<?> save(OrderDTO orderDTO);


}
