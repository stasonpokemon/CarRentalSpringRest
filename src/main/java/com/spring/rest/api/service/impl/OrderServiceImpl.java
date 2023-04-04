package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.*;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.RefundResponseDTO;
import com.spring.rest.api.entity.mapper.OrderMapper;
import com.spring.rest.api.entity.mapper.RefundMapper;
import com.spring.rest.api.exception.BadRequestException;
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
import java.util.UUID;
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
    public ResponseEntity<OrderResponseDTO> findById(UUID orderId) {

        log.info("Finding order by id: {}", orderId);

        OrderResponseDTO orderResponseDTO = orderMapper.orderToOrderResponseDTO(findOrderByIdOrThrowException(orderId));

        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);

        log.info("Find order by id: {}", orderId);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<OrderResponseDTO>> findAll(Pageable pageable) {

        log.info("Finding all orders");

        Page<OrderResponseDTO> orderResponseDTOPage = new PageImpl<>(orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::orderToOrderResponseDTO)
                .collect(Collectors.toList()));

        if (orderResponseDTOPage.isEmpty()) {
            throw new NotFoundException("There are no orders");
        }

        ResponseEntity<Page<OrderResponseDTO>> response = new ResponseEntity<>(orderResponseDTOPage, HttpStatus.OK);

        log.info("Find all orders");

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Page<OrderResponseDTO>> findOrdersByUserId(UUID userId,
                                                                     Pageable pageable) {

        log.info("Finding all orders by user id: {}", userId);

        User user = userService.findUserByIdOrThrowException(userId);

        Page<OrderResponseDTO> orderResponseDTOPage = new PageImpl<>(user.getOrders()
                .stream()
                .map(orderMapper::orderToOrderResponseDTO)
                .collect(Collectors.toList()));

        ResponseEntity<Page<OrderResponseDTO>> response = new ResponseEntity<>(orderResponseDTOPage, HttpStatus.OK);

        log.info("Find all orders by user id: {}", userId);

        return response;
    }


    @Override
    public ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO,
                                                        UUID userId,
                                                        UUID carId) {

        // переделать логику: поместить userId и carId в CreateOrderRequestDTO

        log.info("Creating new order: {} for car with id: {} for user with id: {}",
                createOrderRequestDTO, carId, userId);

        Car car = carService.findCarByIdOrThrowException(carId);

        User user = userService.findUserByIdOrThrowException(userId);

        if (user.getPassport() == null) {
            throw new BadRequestException(
                    "The user must have a passport to create a new order");
        }

        if (car.isDeleted()) {
            throw new BadRequestException(
                    String.format("The car with id = %s is deleted", carId));
        }

        if (car.isBroken()) {
            throw new BadRequestException(
                    String.format("The car with id = %s is broken", carId));
        }

        if (car.isBusy()) {
            throw new BadRequestException(
                    String.format("The car with id = %s isn't free", carId));
        }


        car.setBusy(true);

        Order order = orderMapper.createOrderRequestDTOToOrder(createOrderRequestDTO);
        order.setCar(car);
        order.setUser(user);
        order.setOrderStatus(OrderStatus.UNDER_CONSIDERATION);
        order.setOrderDate(LocalDateTime.now());
        order.setPrice(((double) order.getRentalPeriod() * car.getPricePerDay()));

        OrderResponseDTO orderResponseDTO = orderMapper.orderToOrderResponseDTO(orderRepository.save(order));

        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);

        log.info("Creat new order: {} for user with id: {}", orderResponseDTO, userId);

        return response;
    }

    @Override
    public ResponseEntity<OrderResponseDTO> acceptOrder(UUID orderId) {

        log.info("Accepting order with id: {}", orderId);

        Order order = findOrderByIdOrThrowException(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
            throw new BadRequestException(
                    String.format("Order with id = %s already accepted or canceled", orderId));
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(orderMapper.orderToOrderResponseDTO(order), HttpStatus.OK);

        log.info("Accept order with id: {}", orderId);

        return response;
    }

    @Override
    public ResponseEntity<OrderResponseDTO> cancelOrder(UUID orderId) {

        log.info("Canceling order with id: {}", orderId);

        Order order = findOrderByIdOrThrowException(orderId);

        if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
            throw new BadRequestException(
                    String.format("Order with id = %s already accepted or canceled", orderId));
        }

        order.setOrderStatus(OrderStatus.REFUSAL);
        order.getCar().setBusy(false);
        ResponseEntity<OrderResponseDTO> response = new ResponseEntity<>(orderMapper.orderToOrderResponseDTO(order), HttpStatus.OK);

        log.info("Cancel order with id: {}", orderId);

        return response;
    }

    @Override
    public ResponseEntity<RefundResponseDTO> createOrdersRefund(UUID orderId,
                                                                CreateRefundRequestDTO createRefundRequestDTO) {

        log.info("Creating refund: {} for order with id: {}", createRefundRequestDTO, orderId);

        Order order = findOrderByIdOrThrowException(orderId);

        if (order.getRefund() != null) {
            throw new BadRequestException(
                    String.format("Order with id = %s already has refund", orderId));
        }

        Refund refund = refundMapper.createRefundRequestDTOToRefund(createRefundRequestDTO);
        refund.setRefundDate(LocalDateTime.now());


        if (!refund.isDamaged()) {
            order.getCar().setBusy(false);
            refund.setPrice(0);
            refund.setDamageDescription("");
        } else {
            order.getCar().setBroken(true);
            order.getCar().setDamageStatus(refund.getDamageDescription());
        }

        order.setRefund(refund);

        refund.setOrder(order);

        Refund savedRefund = orderRepository.save(order).getRefund();

        ResponseEntity<RefundResponseDTO> response = new ResponseEntity<>(refundMapper.refundToRefundResponseDTO(savedRefund), HttpStatus.OK);

        log.info("Create refund: {} for order with id: {}", createRefundRequestDTO, orderId);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<RefundResponseDTO> findOrdersRefund(UUID orderId) {

        log.info("Finding order's refund by orderId: {}", orderId);

        Order order = findOrderByIdOrThrowException(orderId);

        if (order.getRefund() == null) {
            throw new BadRequestException(
                    String.format("Order with id = %s hasn't refund", orderId));
        }

        RefundResponseDTO refundResponseDTO = refundMapper.refundToRefundResponseDTO(order.getRefund());

        ResponseEntity<RefundResponseDTO> response = new ResponseEntity<>(refundResponseDTO, HttpStatus.OK);

        log.info("Find order's refund: {} by orderId: {}", refundResponseDTO, orderId);

        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    protected Order findOrderByIdOrThrowException(UUID orderId) {

        log.info("Finding order with id: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Order.class, orderId));

        log.info("Find order with id: {}", orderId);

        return order;
    }

}
