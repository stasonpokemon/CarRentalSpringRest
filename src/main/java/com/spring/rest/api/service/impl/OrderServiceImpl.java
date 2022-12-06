package com.spring.rest.api.service.impl;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.OrderStatus;
import com.spring.rest.api.entity.User;
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
import com.spring.rest.api.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(String[] sort) {
        ResponseEntity<?> response;
        try {
            List<Sort.Order> sortOrders = CommonUtil.getInstance().getOrdersFromRequest(sort, Order.class);
            List<OrderDTO> ordersDTO = orderRepository.findAll(Sort.by(sortOrders)).stream().map(this::orderToOrderDTO).collect(Collectors.toList());
            if (ordersDTO.isEmpty()) {
                return new ResponseEntity<String>("There are no orders", HttpStatus.OK);
            }
            response = new ResponseEntity<List<OrderDTO>>(ordersDTO, HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to get all orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long orderId) {
        ResponseEntity<?> response;
        try {
            OrderDTO orderDTO = orderToOrderDTO(findOrderByIdOrThrowException(orderId));
            response = new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to get order", HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<String>("The user must have a passport to create a new order", HttpStatus.OK);
            }
            if (car.isDeleted()) {
                return new ResponseEntity<String>(new StringBuilder()
                        .append("The car with id = ")
                        .append(carId)
                        .append(" is deleted").toString(), HttpStatus.OK);
            }
            if (!car.isEmploymentStatus()) {
                return new ResponseEntity<String>(new StringBuilder()
                        .append("The car with id = ")
                        .append(carId)
                        .append(" isn't free").toString(), HttpStatus.OK);
            }
            car.setEmploymentStatus(false);
            order.setCar(car);
            order.setUser(user);
            order.setOrderStatus(OrderStatus.UNDER_CONSIDERATION);
            order.setOrderDate(LocalDateTime.now());
            order.setPrice(((double) order.getRentalPeriod() * car.getPricePerDay()));
            OrderDTO orderDTO = orderToOrderDTO(orderRepository.save(order));
            response = new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception exception) {
            response = new ResponseEntity<String>("Unable to create order", HttpStatus.INTERNAL_SERVER_ERROR);
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
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(new StringBuilder()
                .append("Order with id = ")
                .append(orderId)
                .append(" not found").toString()));

    }

}
