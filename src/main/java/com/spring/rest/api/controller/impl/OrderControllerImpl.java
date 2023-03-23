package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.OrderController;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("GET request to find all orders");

        return orderService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findById(Long orderId) {

        log.info("GET request to find order with id: {}", orderId);

        return orderService.findById(orderId);
    }

    @Override
    public ResponseEntity<?> findOrdersRefund(Long orderId) {

        log.info("GET request to find order's refund with orderId: {}", orderId);

        return orderService.findOrdersRefund(orderId);
    }

    @Override
    public ResponseEntity<?> createOrder(Long userId,
                                         Long carId,
                                         CreateOrderRequestDTO createOrderRequestDTO) {

        log.info("POST request to create order: {} for car with id: {} for user with id: {}",
                createOrderRequestDTO, carId, userId);

        return orderService.createOrder(createOrderRequestDTO, userId, carId);
    }

    @Override
    public ResponseEntity<?> acceptOrder(Long orderId) {

        log.info("PATCH request to accept order with id: {}", orderId);

        return orderService.acceptOrder(orderId);
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long orderId) {

        log.info("PATCH request to cancel order with id: {}", orderId);

        return orderService.cancelOrder(orderId);
    }

    @Override
    public ResponseEntity<?> createOrdersRefund(Long orderId,
                                                CreateRefundRequestDTO createRefundRequestDTO) {

        log.info("POST request to create refund: {} for order with id: {}", createRefundRequestDTO, orderId);

        return orderService.createOrdersRefund(orderId, createRefundRequestDTO);
    }


}
