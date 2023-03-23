package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.OrderController;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findById(Long orderId) {
        return orderService.findById(orderId);
    }

    @Override
    public ResponseEntity<?> findOrdersRefund(Long orderId) {
        return orderService.findOrdersRefund(orderId);
    }

    @Override
    public ResponseEntity<?> createOrder(Long userId,
                                         Long carId,
                                         CreateOrderRequestDTO createOrderRequestDTO) {
        return orderService.createOrder(createOrderRequestDTO, userId, carId);
    }

    @Override
    public ResponseEntity<?> acceptOrder(Long orderId) {
        return orderService.acceptOrder(orderId);
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @Override
    public ResponseEntity<?> createOrdersRefund(Long orderId,
                                                CreateRefundRequestDTO createRefundRequestDTO) {
        return orderService.createOrdersRefund(orderId, createRefundRequestDTO);
    }


}
