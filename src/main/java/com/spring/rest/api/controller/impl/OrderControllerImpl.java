package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.OrderController;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import com.spring.rest.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Implementation class for OrderController.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Page<OrderResponseDTO>> findAll(Pageable pageable) {

        log.info("GET request to find all orders");

        return orderService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findById(UUID orderId) {

        log.info("GET request to find order with id: {}", orderId);

        return orderService.findById(orderId);
    }

    @Override
    public ResponseEntity<RefundResponseDTO> findOrdersRefund(UUID orderId) {

        log.info("GET request to find order's refund with orderId: {}", orderId);

        return orderService.findOrdersRefundByOrderId(orderId);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO) {

        log.info("POST request to create order: {}",
                createOrderRequestDTO);

        return orderService.createOrder(createOrderRequestDTO);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId) {

        log.info("PATCH request to accept order with id: {}", orderId);

        return orderService.acceptOrder(orderId);
    }

    @Override
    public ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId) {

        log.info("PATCH request to cancel order with id: {}", orderId);

        return orderService.cancelOrder(orderId);
    }

    @Override
    public ResponseEntity<RefundResponseDTO> createOrdersRefund(CreateRefundRequestDTO createRefundRequestDTO) {

        log.info("POST request to create refund: {}", createRefundRequestDTO);

        return orderService.createOrdersRefund(createRefundRequestDTO);
    }


}
