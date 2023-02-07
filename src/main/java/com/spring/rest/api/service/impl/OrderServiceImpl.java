package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.*;
import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.entity.dto.OrderDTO;
import com.spring.rest.api.entity.dto.RefundDTO;
import com.spring.rest.api.entity.dto.UserDTO;
import com.spring.rest.api.exception.EntityNotFoundException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import com.spring.rest.api.repo.OrderRepository;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CarService carService;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CarService carService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long orderId) {
        ResponseEntity<?> response;
        try {
            OrderDTO orderDTO = orderToOrderDTO(findOrderByIdOrThrowException(orderId));
            response = new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to get order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) {
        ResponseEntity<?> response;
        try {
            List<OrderDTO> ordersDTO = orderRepository.findAll(pageable).stream().map(this::orderToOrderDTO).collect(Collectors.toList());
            if (ordersDTO.isEmpty()) {
                return new ResponseEntity<>("There are no orders", HttpStatus.OK);
            }
            response = new ResponseEntity<Page<OrderDTO>>(new PageImpl<>(ordersDTO), HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to get all orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findOrdersByUserId(Long userId, Pageable pageable) {
        ResponseEntity<?> response;
        try {
            User user = userService.findUserByIdOrThrowException(userId);
            if (user.getOrders().isEmpty()) {
                return new ResponseEntity<>(
                        String.format("User with id = %s hasn't orders", userId), HttpStatus.OK);
            }
            List<OrderDTO> ordersDTO = orderRepository.findAllByUserId(userId, pageable).stream().map(this::orderToOrderDTO).collect(Collectors.toList());
            response = new ResponseEntity<Page<OrderDTO>>(new PageImpl<>(ordersDTO), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to get orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Override
    public ResponseEntity<?> createOrder(Order order, Long userId, Long carId) {
        ResponseEntity<?> response;
        try {
            Car car = carService.findCarByIdOrThrowException(carId);
            User user = userService.findUserByIdOrThrowException(userId);
            if (user.getPassport() == null) {
                return new ResponseEntity<>("The user must have a passport to create a new order", HttpStatus.OK);
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
            OrderDTO orderDTO = orderToOrderDTO(orderRepository.save(order));
            response = new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to create order", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public ResponseEntity<?> acceptOrder(Long orderId) {
        ResponseEntity<?> response;
        try {
            Order order = findOrderByIdOrThrowException(orderId);
            if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
                return new ResponseEntity<>(
                        String.format("Order with id = %s already accepted or canceled", orderId), HttpStatus.OK);
            }
            order.setOrderStatus(OrderStatus.CONFIRMED);
            response = new ResponseEntity<>(orderToOrderDTO(order), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to cancel order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long orderId) {
        ResponseEntity<?> response;
        try {
            Order order = findOrderByIdOrThrowException(orderId);
            if (!order.getOrderStatus().equals(OrderStatus.UNDER_CONSIDERATION)) {
                return new ResponseEntity<>(
                        String.format("Order with id = %s already accepted or canceled", orderId), HttpStatus.OK);
            }
            order.setOrderStatus(OrderStatus.REFUSAL);
            order.getCar().setEmploymentStatus(true);
            response = new ResponseEntity<>(orderToOrderDTO(order), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to cancel order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> createOrdersRefund(Long orderId, RefundDTO refundDTO) {
        ResponseEntity<?> response;
        try {
            Order order = findOrderByIdOrThrowException(orderId);
            if (order.getRefund() != null) {
                return new ResponseEntity<>(
                        String.format("Order with id = %s already has refund", orderId), HttpStatus.OK);
            }
            refundDTO.setRefundDate(LocalDateTime.now());
            Refund refund = modelMapper.map(refundDTO, Refund.class);
            if (!refund.isDamaged()) {
                order.getCar().setEmploymentStatus(true);
                refund.setPrice(0);
                refund.setDamageDescription("");
            } else {
                order.getCar().setBroken(true);
                order.getCar().setDamageStatus(refund.getDamageDescription());
            }
            order.setRefund(refund);
            response = new ResponseEntity<>(orderToOrderDTO(order), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to create order's refund", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findOrdersRefund(Long orderId) {
        ResponseEntity<?> response;
        try {
            Order order = findOrderByIdOrThrowException(orderId);
            if (order.getRefund() == null) {
                return new ResponseEntity<>(
                        String.format("Order with id = %s hasn't refund", orderId), HttpStatus.OK);
            }
            RefundDTO refundDTO = modelMapper.map(order.getRefund(), RefundDTO.class);
            response = new ResponseEntity<>(refundDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<>("Unable to get order's refund", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    private OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        if (order.getCar() != null) {
            orderDTO.setCar(modelMapper.map(order.getCar(), CarDTO.class));
        }
        if (order.getUser() != null) {
            orderDTO.setUser(modelMapper.map(order.getUser(), UserDTO.class));
        }
        if (order.getRefund() != null) {
            orderDTO.setRefund(modelMapper.map(order.getRefund(), RefundDTO.class));
        }
        return orderDTO;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Order findOrderByIdOrThrowException(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(
                String.format("Order with id = %s not found", orderId)));
    }

}
