package com.spring.rest.api.controller;

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
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId);
    }

    @GetMapping("/{id}/refund")
    public ResponseEntity<?> findOrdersRefund(@PathVariable("id") Long orderId) {
        return orderService.findOrdersRefund(orderId);
    }

    @PostMapping("/{userId}/{carId}")
    public ResponseEntity<?> createOrder(@PathVariable("userId") Long userId,
                                         @PathVariable("carId") Long carId,
                                         @RequestBody @Valid Order order,
                                         BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return orderService.createOrder(order, userId, carId);
    }

    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable("orderId") Long orderId) {
        return orderService.acceptOrder(orderId);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PostMapping("/{orderId}/refund")
    public ResponseEntity<?> createOrdersRefund(@PathVariable("orderId") Long orderId,
                                                @RequestBody @Valid RefundDTO refundDTO,
                                                BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return orderService.createOrdersRefund(orderId, refundDTO);
    }


}
