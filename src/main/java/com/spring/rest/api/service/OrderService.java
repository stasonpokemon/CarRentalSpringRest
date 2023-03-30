package com.spring.rest.api.service;

import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface OrderService {
    ResponseEntity<Page<OrderResponseDTO>> findAll(Pageable pageable);

    ResponseEntity<Page<OrderResponseDTO>> findOrdersByUserId(UUID userId,
                                                              Pageable pageable);

    ResponseEntity<OrderResponseDTO> findById(UUID orderId);

    ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO,
                                                 UUID userId,
                                                 UUID carId);

    ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId);

    ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId);

    ResponseEntity<RefundResponseDTO> createOrdersRefund(UUID orderId,
                                                         CreateRefundRequestDTO createRefundRequestDTO);

    ResponseEntity<RefundResponseDTO> findOrdersRefund(UUID orderId);
}
