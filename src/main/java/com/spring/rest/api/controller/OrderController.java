package com.spring.rest.api.controller;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.RefundDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/orders")
public interface OrderController {

    @GetMapping
    ResponseEntity<?> findAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<?> findById(
            @PathVariable("id") Long orderId);

    @GetMapping("/{id}/refund")
    ResponseEntity<?> findOrdersRefund(
            @PathVariable("id") Long orderId);

    @PostMapping("/{userId}/{carId}")
    ResponseEntity<?> createOrder(
            @PathVariable("userId") Long userId,
            @PathVariable("carId") Long carId,
            @RequestBody @Valid Order order);

    @PatchMapping("/{orderId}/accept")
    ResponseEntity<?> acceptOrder(
            @PathVariable("orderId") Long orderId);

    @PatchMapping("/{orderId}/cancel")
    ResponseEntity<?> cancelOrder(
            @PathVariable("orderId") Long orderId);

    @PostMapping("/{orderId}/refund")
    ResponseEntity<?> createOrdersRefund
            (@PathVariable("orderId") Long orderId,
             @RequestBody @Valid RefundDTO refundDTO);


}
