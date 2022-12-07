package com.spring.rest.api.controller;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.Refund;
import com.spring.rest.api.entity.dto.RefundDTO;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> findAll(@RequestParam(name = "sort", defaultValue = "id,acs", required = false) String[] sort) {
        return orderService.findAll(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long orderId) {
        return orderService.findById(orderId);
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
