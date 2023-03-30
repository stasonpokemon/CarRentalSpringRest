package com.spring.rest.api.service;

import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface OrderService {
    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> findOrdersByUserId(UUID userId,
                                         Pageable pageable);

    ResponseEntity<?> findById(UUID orderId);

    ResponseEntity<?> createOrder(CreateOrderRequestDTO createOrderRequestDTO,
                                  UUID userId,
                                  UUID carId);

    ResponseEntity<?> acceptOrder(UUID orderId);

    ResponseEntity<?> cancelOrder(UUID orderId);

    ResponseEntity<?> createOrdersRefund(UUID orderId,
                                         CreateRefundRequestDTO createRefundRequestDTO);

    ResponseEntity<?> findOrdersRefund(UUID orderId);
}
