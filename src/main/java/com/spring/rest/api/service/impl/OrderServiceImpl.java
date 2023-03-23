package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.*;
import com.spring.rest.api.entity.dto.OrderDTO;
import com.spring.rest.api.entity.dto.RefundDTO;
import com.spring.rest.api.entity.mapper.OrderMapper;
import com.spring.rest.api.entity.mapper.RefundMapper;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.OrderRepository;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CarService carService;

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    private final RefundMapper refundMapper = Mappers.getMapper(RefundMapper.class);

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long orderId) {

        log.info("Finding order by id: {}", orderId);

        OrderDTO orderDTO = orderMapper.orderToOrderDTO(findOrderByIdOrThrowException(orderId));
        ResponseEntity<?> response = new ResponseEntity<>(orderDTO, HttpStatus.OK);

        log.info("Find order by id: {}", orderId);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("Finding all orders");

        ResponseEntity<?> response;

        List<OrderDTO> ordersDTO = orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());

        if (ordersDTO.isEmpty()) {
            return new ResponseEntity<>("There are no orders", HttpStatus.OK);
        }

        response = new ResponseEntity<Page<OrderDTO>>(new PageImpl<>(ordersDTO), HttpStatus.OK);

        log.info("Find all orders");

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findOrdersByUserId(Long userId, Pageable pageable) {

        log.info("Finding all orders by user id: {}", userId);

        ResponseEntity<?> response;

        User user = userService.findUserByIdOrThrowException(userId);

        if (user.getOrders().isEmpty()) {
            return new ResponseEntity<>(
                    String.format("User with id = %s hasn't orders", userId), HttpStatus.OK);
        }

        List<OrderDTO> ordersDTO = orderRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());

        response = new ResponseEntity<Page<OrderDTO>>(new PageImpl<>(ordersDTO), HttpStatus.OK);

        log.info("Find all orders by user id: {}", userId);

        return response;
    }


    @Override
    public ResponseEntity<?> createOrder(Order order, Long userId, Long carId) {

        log.info("Creating new order: {} for user with id: {}", order, userId);

        ResponseEntity<?> response;

        Car car = carService.findCarByIdOrThrowException(carId);
        User user = userService.findUserByIdOrThrowException(userId);

        if (user.getPassport() == null) {
            return new ResponseEntity<>(
                    "The user must have a passport to create a new order", HttpStatus.OK);
        }

        if (car.isDeleted()) {
            return new ResponseEntity<>(
                    String.format("The car with id = %s is deleted", carId), HttpStatus.OK);
        }

        if (!car.isEmploymentStatus()) {
            return new ResponseEntity<>(
                    String.format("The car with id = %s isn't free", carId), HttpStatus.OK);
        }

        car.setEmploymentStatus(false);
        order.setCar(car);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.UNDER_CONSIDERATION);
        order.setOrderDate(LocalDateTime.now());
        order.setPrice(((double) order.getRentalPeriod() * car.getPricePerDay()));
        OrderDTO orderDTO = orderMapper.orderToOrderDTO(orderRepository.save(order));
        response = new ResponseEntity<>(orderDTO, HttpStatus.OK);

        log.info("Creat new order: {} for user with id: {}", orderDTO, userId);

        return response;
    }

    @Override
    public ResponseEntity<?> acceptOrder(Long orderId) {

        log.info("Accepting order with id: {}", orderId);

        ResponseEntity<?> response;

        Order order = findOrderByIdOrThrowException(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
            return new ResponseEntity<>(
                    String.format("Order with id = %s already accepted or canceled", orderId), HttpStatus.OK);
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        response = new ResponseEntity<>(orderMapper.orderToOrderDTO(order), HttpStatus.OK);

        log.info("Accept order with id: {}", orderId);

        return response;
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long orderId) {

        log.info("Canceling order with id: {}", orderId);

        ResponseEntity<?> response;

        Order order = findOrderByIdOrThrowException(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
            return new ResponseEntity<>(
                    String.format("Order with id = %s already accepted or canceled", orderId), HttpStatus.OK);
        }

        order.setOrderStatus(OrderStatus.REFUSAL);
        order.getCar().setEmploymentStatus(true);
        response = new ResponseEntity<>(orderMapper.orderToOrderDTO(order), HttpStatus.OK);

        log.info("Cancel order with id: {}", orderId);

        return response;
    }

    @Override
    public ResponseEntity<?> createOrdersRefund(Long orderId, RefundDTO refundDTO) {

        log.info("Creating refund: {} for order with id: {}", refundDTO, orderId);

        ResponseEntity<?> response;

        Order order = findOrderByIdOrThrowException(orderId);

        if (order.getRefund() != null) {
            return new ResponseEntity<>(
                    String.format("Order with id = %s already has refund", orderId), HttpStatus.OK);
        }

        refundDTO.setRefundDate(LocalDateTime.now());

        Refund refund = refundMapper.refundDTOToRefund(refundDTO);

        if (!refund.isDamaged()) {
            order.getCar().setEmploymentStatus(true);
            refund.setPrice(0);
            refund.setDamageDescription("");
        } else {
            order.getCar().setBroken(true);
            order.getCar().setDamageStatus(refund.getDamageDescription());
        }

        order.setRefund(refund);
        response = new ResponseEntity<>(orderMapper.orderToOrderDTO(order), HttpStatus.OK);

        log.info("Create refund: {} for order with id: {}", refundDTO, orderId);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findOrdersRefund(Long orderId) {

        log.info("Finding order's refund by orderId: {}", orderId);

        ResponseEntity<?> response;

        Order order = findOrderByIdOrThrowException(orderId);

        if (order.getRefund() == null) {
            return new ResponseEntity<>(
                    String.format("Order with id = %s hasn't refund", orderId), HttpStatus.OK);
        }

        RefundDTO refundDTO = refundMapper.refundToRefundDTO(order.getRefund());

        response = new ResponseEntity<>(refundDTO, HttpStatus.OK);

        log.info("Find order's refund: {} by orderId: {}", refundDTO, orderId);

        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Order findOrderByIdOrThrowException(Long orderId) {

        log.info("Finding order with id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Order.class, orderId));

        log.info("Find order with id: {}", orderId);

        return order;
    }

}
