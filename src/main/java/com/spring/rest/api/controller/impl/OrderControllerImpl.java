package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.OrderController;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.RefundDTO;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<?> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId);
    }

    @Override
    public ResponseEntity<?> findOrdersRefund(@PathVariable("id") Long orderId) {
        return orderService.findOrdersRefund(orderId);
    }

    @Override
    public ResponseEntity<?> createOrder(@PathVariable("userId") Long userId, @PathVariable("carId") Long carId, @RequestBody @Valid Order order, BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return orderService.createOrder(order, userId, carId);
    }

    @Override
    public ResponseEntity<?> acceptOrder(@PathVariable("orderId") Long orderId) {
        return orderService.acceptOrder(orderId);
    }

    @Override
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) {
        return orderService.cancelOrder(orderId);
    }


    @Override
    public ResponseEntity<?> createOrdersRefund(@PathVariable("orderId") Long orderId, @RequestBody @Valid RefundDTO refundDTO, BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return orderService.createOrdersRefund(orderId, refundDTO);
    }


}
