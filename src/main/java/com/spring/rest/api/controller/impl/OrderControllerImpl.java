package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.OrderController;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.RefundDTO;
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
                                         Long carId, Order order) {
        return orderService.createOrder(order, userId, carId);
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
                                                RefundDTO refundDTO) {
        return orderService.createOrdersRefund(orderId, refundDTO);
    }


}
