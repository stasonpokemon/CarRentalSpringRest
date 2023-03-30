package com.spring.rest.api.service;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.OrderRepository;
import com.spring.rest.api.service.impl.OrderServiceImpl;
import com.spring.rest.api.util.OrderTestDataFactory;
import com.spring.rest.api.util.UserTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    private UUID orderId;

    private Order firsrOrder;

    private Order secondOrder;

    private OrderResponseDTO firstOrderRequestDTO;

    private OrderResponseDTO secondOrderRequestDTO;

    private Page<Order> orderPage;

    private Page<OrderResponseDTO> orderResponseDTOS;

    private Pageable pageRequest;


    @BeforeEach
    void init() {
        orderId = UUID.randomUUID();

        firsrOrder = OrderTestDataFactory.buildNewOrder(UserTestDataFactory.buildUserWithPassport());

        secondOrder = OrderTestDataFactory.buildNewOrder(UserTestDataFactory.buildUserWithPassport());

        firstOrderRequestDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(firsrOrder);

        secondOrderRequestDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(secondOrder);

        orderPage = new PageImpl<>(List.of(firsrOrder, secondOrder));

        orderResponseDTOS = new PageImpl<>(List.of(firstOrderRequestDTO, secondOrderRequestDTO));

        orderPage = new PageImpl<>(List.of(firsrOrder, secondOrder));

        orderResponseDTOS = new PageImpl<>(List.of(firstOrderRequestDTO, secondOrderRequestDTO));

        pageRequest = PageRequest.of(0, 2);

    }

    @Test
    void findById_WhenOrderWithSpecifiedIdIsExist_ReturnOrderResponseDTO() {
        //given - precondition or setup
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(firsrOrder));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = orderService.findById(orderId);
        OrderResponseDTO responseBody = (OrderResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(OrderTestDataFactory.buildOrderToOrderResponseDTO(firsrOrder), responseBody);

        verify(orderRepository).findById(orderId);
    }

    @Test
    void findById_WhenOrderWithSpecifiedIdIsNorExist_ThrowsNotFoundException() {
        //given - precondition or setup
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> orderService.findById(orderId));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(String.format("Not found Order with id: %s", orderId), notFoundException.getMessage());

        verify(orderRepository).findById(orderId);
    }

    @Test
    void findAll_WhenOrdersExist_ReturnOrdersPage() {
        //given - precondition or setup
        when(orderRepository.findAll(pageRequest)).thenReturn(orderPage);

        //when - action or the behaviour that we are going test
        ResponseEntity<Page<OrderResponseDTO>> response = orderService.findAll(pageRequest);
        Page<OrderResponseDTO> responseBody = response.getBody();

        assertNotNull(response);
        assertNotNull(responseBody);

        //then - verify the output

        verify(orderRepository).findAll(pageRequest);
    }

    @Test
    void findAll_WhenOrdersIsNorExist_ThrowsNotFound() {
        //given - precondition or setup
        when(orderRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(Collections.emptyList()));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> orderService.findAll(pageRequest));

        //then - verify the output
        assertEquals("There are no orders", notFoundException.getMessage());

        verify(orderRepository).findAll(pageRequest);
    }

    @Test
    void findOrdersByUserId_WhenOrderWithSpecifiedIdIsExists_ReturnOrderResponseDTO() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void findOrdersByUserId_WhenOrderWithSpecifiedIdIsNorExists_ReturnOrderResponseDTO() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void createOrder() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void acceptOrder() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void cancelOrder() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void createOrdersRefund() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void findOrdersRefund() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void findOrderByIdOrThrowException() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }
}