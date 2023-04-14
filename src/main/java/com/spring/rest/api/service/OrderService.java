package com.spring.rest.api.service;

import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * The OrderService interface, which stores the business logic for working with an order.
 */
public interface OrderService {
    ResponseEntity<Page<OrderResponseDTO>> findAll(Pageable pageable);

    ResponseEntity<Page<OrderResponseDTO>> findOrdersByUserId(UUID userId,
                                                              Pageable pageable);

    ResponseEntity<OrderResponseDTO> findById(UUID orderId);

    ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO);

    ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId);

    ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId);

    ResponseEntity<RefundResponseDTO> createOrdersRefund(CreateRefundRequestDTO createRefundRequestDTO);

    ResponseEntity<RefundResponseDTO> findOrdersRefundByOrderId(UUID orderId);
}
