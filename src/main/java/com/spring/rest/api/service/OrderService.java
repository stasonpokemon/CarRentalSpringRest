package com.spring.rest.api.service;

import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> findOrdersByUserId(Long userId,
                                         Pageable pageable);

    ResponseEntity<?> findById(Long orderId);

    ResponseEntity<?> createOrder(CreateOrderRequestDTO createOrderRequestDTO,
                                  Long userId,
                                  Long carId);

    ResponseEntity<?> acceptOrder(Long orderId);

    ResponseEntity<?> cancelOrder(Long orderId);

    ResponseEntity<?> createOrdersRefund(Long orderId,
                                         CreateRefundRequestDTO createRefundRequestDTO);

    ResponseEntity<?> findOrdersRefund(Long orderId);
}
