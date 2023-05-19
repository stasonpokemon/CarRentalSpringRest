package com.spring.rest.api.service;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.User;
import com.spring.rest.api.entity.dto.request.CreateOrderRequestDTO;
import com.spring.rest.api.entity.dto.request.CreateRefundRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.exception.BadRequestException;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.OrderRepository;
import com.spring.rest.api.service.impl.OrderServiceImpl;
import com.spring.rest.api.util.CarTestDataFactory;
import com.spring.rest.api.util.OrderTestDataFactory;
import com.spring.rest.api.util.RefundTestDataFactory;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private UUID userId;

    private UUID carId;

    private User userWithPassportAndOrders;

    private User userWithoutOrders;

    private User userWithoutPassport;

    private Order firsrOrder;

    private Order secondOrder;

    private Order acceptedFirstOrder;

    private Order canceledFirstOrder;

    private Car freeNotBusyCar;

    private Car deletedCar;

    private Car brokenCar;

    private Car busyCar;

    private CreateOrderRequestDTO createOrderRequestDTO;

    private OrderResponseDTO firstOrderRequestDTO;

    private OrderResponseDTO secondOrderRequestDTO;

    private OrderResponseDTO acceptedFirstOrderResponseDTO;

    private OrderResponseDTO canceledFirstOrderResponseDTO;

    private CreateRefundRequestDTO createRefundRequestDTOWithoutDamage;

    private CreateRefundRequestDTO createRefundRequestDTOWithDamage;

    private Page<Order> orderPage;

    private Page<OrderResponseDTO> orderResponseDTOS;

    private Page<OrderResponseDTO> orderResponseDTOPageFromUser;

    private Pageable pageRequest;


    @BeforeEach
    void init() {
        orderId = UUID.randomUUID();

        userId = UUID.randomUUID();

        carId = UUID.randomUUID();

        userWithPassportAndOrders = UserTestDataFactory.buildUserWithOrders();

        userWithoutOrders = UserTestDataFactory.buildUserWithoutPassport();

        userWithoutPassport = UserTestDataFactory.buildUserWithoutPassport();

        orderResponseDTOPageFromUser = OrderTestDataFactory.buildOrderResponseDTOPageFromUser(userWithPassportAndOrders);

        freeNotBusyCar = CarTestDataFactory.buildCar();

        deletedCar = CarTestDataFactory.buildDeletedCar();

        brokenCar = CarTestDataFactory.buildBrokenCar();

        busyCar = CarTestDataFactory.buildBusyCar();

        firsrOrder = OrderTestDataFactory.buildOrder(userWithPassportAndOrders);

        secondOrder = OrderTestDataFactory.buildOrder(userWithPassportAndOrders);

        acceptedFirstOrder = OrderTestDataFactory.buildAcceptedOrder(firsrOrder);

        canceledFirstOrder = OrderTestDataFactory.buildCanceledOrder(firsrOrder);

        firstOrderRequestDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(firsrOrder);

        secondOrderRequestDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(secondOrder);

        acceptedFirstOrderResponseDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(acceptedFirstOrder);

        canceledFirstOrderResponseDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(canceledFirstOrder);

        createRefundRequestDTOWithoutDamage = RefundTestDataFactory.buildCreateRefundRequestDTOWithoutDamage();

        createRefundRequestDTOWithDamage = RefundTestDataFactory.buildCreateRefundRequestDTOWithDamage();

        createOrderRequestDTO = OrderTestDataFactory.buildCreateOrderRequestDTO(carId, userId);

        orderPage = new PageImpl<>(List.of(firsrOrder, secondOrder));

        orderResponseDTOS = new PageImpl<>(List.of(firstOrderRequestDTO, secondOrderRequestDTO));

        orderPage = new PageImpl<>(List.of(firsrOrder, secondOrder));

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
    void findOrdersByUserId_WhenUserIsExistsAndUserHasOrders_ReturnOrderResponseDTOPage() {
        //given - precondition or setup
        Page<OrderResponseDTO> expectedOrderResponseDTOPage = orderResponseDTOPageFromUser;
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithPassportAndOrders);

        //when - action or the behaviour that we are going test
        ResponseEntity<Page<OrderResponseDTO>> response = orderService.findOrdersByUserId(userId, pageRequest);
        Page<OrderResponseDTO> responseBody = response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrderResponseDTOPage, responseBody);

        verify(userService).findUserByIdOrThrowException(userId);
    }

    @Test
    void findOrdersByUserId_WhenUserIsExistsAndUserHasNotOrders_ReturnEmptyOrderResponseDTOPage() {
        //given - precondition or setup
        Page<OrderResponseDTO> expectedEmptyOrderResponseDTOPage = new PageImpl<>(Collections.emptyList());
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithoutOrders);

        //when - action or the behaviour that we are going test
        ResponseEntity<Page<OrderResponseDTO>> response = orderService.findOrdersByUserId(userId, pageRequest);
        Page<OrderResponseDTO> responseBody = response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEmptyOrderResponseDTOPage, responseBody);

        verify(userService).findUserByIdOrThrowException(userId);
    }

    @Test
    void findOrdersByUserId_WhenOrderIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Not found User with id: %s", userId);
        when(userService.findUserByIdOrThrowException(userId))
                .thenThrow(new NotFoundException(User.class, userId));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException =
                assertThrows(NotFoundException.class, () -> orderService.findOrdersByUserId(userId, pageRequest));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(expectedExceptionMessage, notFoundException.getMessage());

        verify(userService).findUserByIdOrThrowException(userId);
    }

    @Test
    void createOrder_WhenCarIsExistsAndIsNotDeletedAndIsNotBusyAndIsNotBrokenAndUserIsExistsAndHasPassport_ReturnOrderResponseDTO() {
        //given - precondition or setup
        freeNotBusyCar.setId(carId);
        userWithPassportAndOrders.setId(userId);

        Order savedOrder = OrderTestDataFactory.buildOrder(createOrderRequestDTO, userWithPassportAndOrders, freeNotBusyCar);
        savedOrder.setId(carId);

        OrderResponseDTO expectedSavedOrderResponseDTO = OrderTestDataFactory.buildOrderToOrderResponseDTO(savedOrder);
        expectedSavedOrderResponseDTO.setId(carId);

        when(carService.findCarByIdOrThrowException(carId)).thenReturn(freeNotBusyCar);
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithPassportAndOrders);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        //when - action or the behaviour that we are going test
        ResponseEntity<OrderResponseDTO> response = orderService.createOrder(createOrderRequestDTO);
        OrderResponseDTO responseBody = response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSavedOrderResponseDTO, responseBody);

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService).findUserByIdOrThrowException(userId);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_WhenCarIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Not found Car with id: %s", carId);
        when(carService.findCarByIdOrThrowException(carId))
                .thenThrow(new NotFoundException(Car.class, carId));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> orderService.createOrder(createOrderRequestDTO));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(expectedExceptionMessage, notFoundException.getMessage());

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService, never()).findUserByIdOrThrowException(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_WhenCarIsExistsAndUserIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Not found User with id: %s", userId);
        when(carService.findCarByIdOrThrowException(carId)).thenReturn(freeNotBusyCar);
        when(userService.findUserByIdOrThrowException(userId))
                .thenThrow(new NotFoundException(User.class, userId));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> orderService.createOrder(createOrderRequestDTO));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(expectedExceptionMessage, notFoundException.getMessage());

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService).findUserByIdOrThrowException(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_WhenCarIsExistsAndUserIsExistsAndHasNotPassport_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = "The user must have a passport to create a new order";
        when(carService.findCarByIdOrThrowException(carId)).thenReturn(freeNotBusyCar);
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithoutPassport);

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> orderService.createOrder(createOrderRequestDTO));

        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService).findUserByIdOrThrowException(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_WhenUserIsExistsAndHasPassportAndCarIsExistsAndIsDeleted_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("The car with id = %s is deleted", carId);
        when(carService.findCarByIdOrThrowException(carId)).thenReturn(deletedCar);
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithPassportAndOrders);

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> orderService.createOrder(createOrderRequestDTO));

        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService).findUserByIdOrThrowException(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_WhenUserIsExistsAndHasPassportAndCarIsExistsAndIsNotDeletedAndIsBusy_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("The car with id = %s isn't free", carId);

        when(carService.findCarByIdOrThrowException(carId)).thenReturn(busyCar);
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithPassportAndOrders);

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> orderService.createOrder(createOrderRequestDTO));

        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService).findUserByIdOrThrowException(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_WhenUserIsExistsAndHasPassportAndCarIsExistsAndIsNotDeletedAndIsNotBusyAndIsBroken_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("The car with id = %s is broken", carId);

        when(carService.findCarByIdOrThrowException(carId)).thenReturn(brokenCar);
        when(userService.findUserByIdOrThrowException(userId)).thenReturn(userWithPassportAndOrders);

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> orderService.createOrder(createOrderRequestDTO));

        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(carService).findCarByIdOrThrowException(carId);
        verify(userService).findUserByIdOrThrowException(userId);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void acceptOrder_WhenOrderIsExistsAndOrderStatusEqualsUNDER_CONSIDERATION_ReturnOrderResponseDTO() {
        //given - precondition or setup
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(firsrOrder));

        //when - action or the behaviour that we are going test
        ResponseEntity<OrderResponseDTO> response = orderService.acceptOrder(orderId);
        OrderResponseDTO responseBody = response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acceptedFirstOrderResponseDTO, responseBody);

        verify(orderRepository).findById(orderId);
    }

    @Test
    void acceptOrder_WhenOrderIsExistsAndOrderStatusNotEqualsUNDER_CONSIDERATION_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Order with id = %s already accepted or canceled", orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(acceptedFirstOrder));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> orderService.acceptOrder(orderId));


        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(orderRepository).findById(orderId);
    }

    @Test
    void acceptOrder_WhenOrderIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Not found Order with id: %s", orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException badRequestException
                = assertThrows(NotFoundException.class, () -> orderService.acceptOrder(orderId));


        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(orderRepository).findById(orderId);
    }

    @Test
    void cancelOrder_WhenOrderIsExistsAndOrderStatusEqualsUNDER_CONSIDERATION_ReturnOrderResponseDTO() {
        //given - precondition or setup
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(firsrOrder));

        //when - action or the behaviour that we are going test
        ResponseEntity<OrderResponseDTO> response = orderService.cancelOrder(orderId);
        OrderResponseDTO responseBody = response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(canceledFirstOrderResponseDTO, responseBody);

        verify(orderRepository).findById(orderId);
    }

    @Test
    void cancelOrder_WhenOrderIsExistsAndOrderStatusDoesNotEqualsUNDER_CONSIDERATION_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Order with id = %s already accepted or canceled", orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(acceptedFirstOrder));

        //when - action or the behaviour that we are going test
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> orderService.cancelOrder(orderId));


        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(orderRepository).findById(orderId);
    }

    @Test
    void cancelOrder_WhenOrderIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Not found Order with id: %s", orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException badRequestException
                = assertThrows(NotFoundException.class, () -> orderService.cancelOrder(orderId));


        //then - verify the output
        assertNotNull(badRequestException);
        assertEquals(expectedExceptionMessage, badRequestException.getMessage());

        verify(orderRepository).findById(orderId);
    }

    @Test
    void createOrdersRefund_WhenOrderIsExistsAndHasNotRefundAndRefundWithoutDamage_ReturnRefundResponseDTO() {
        //given - precondition or setup
//        Refund refund = RefundTestDataFactory
//                .buildRefundWithoutDamage(createRefundRequestDTOWithoutDamage, acceptedFirstOrder);
//        RefundResponseDTO expectedRefundResponseDTO = RefundTestDataFactory.buildRefundResponseDTO(refund);
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(acceptedFirstOrder));
//        when(orderRepository.save(acceptedFirstOrder)).thenReturn(refund.getOrder());
//
//        //when - action or the behaviour that we are going test
//        ResponseEntity<RefundResponseDTO> response = orderService.createOrdersRefund(orderId, createRefundRequestDTOWithoutDamage);
//        RefundResponseDTO responseBody = response.getBody();
//
//        //then - verify the output
//
//        assertNotNull(response);
//        assertNotNull(responseBody);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        verify(orderRepository).findById(orderId);
//        verify(orderRepository).save(acceptedFirstOrder);
    }

    @Test
    void createOrdersRefund_WhenOrderIsExistsAndHasNotRefundAndRefundWithDamage_ReturnRefundResponseDTO() {
        //given - precondition or setup
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(acceptedFirstOrder));


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void createOrdersRefund_WhenOrderIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
//        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void createOrdersRefund_WhenOrderIsExistsAndHasRefund_ThrowsBadRequestException() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void findOrdersRefund_WhenOrderIsExistsAndHasRefund_ReturnRefundResponseDTO() {
        //given - precondition or setup


        //when - action or the behaviour that we are going test

        //then - verify the output
    }

    @Test
    void findOrdersRefund_WhenOrderIsNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Not found Order with id: %s", orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> orderService.findOrdersRefundByOrderId(orderId));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(expectedExceptionMessage, notFoundException.getMessage());

        verify(orderRepository).findById(orderId);
    }

    @Test
    void findOrdersRefund_WhenOrderIsExistsAndHasNotRefund_ThrowsBadRequestException() {
        //given - precondition or setup
        String expectedExceptionMessage = String.format("Order with id = %s hasn't refund", orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(firsrOrder));

        //when - action or the behaviour that we are going test
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> orderService.findOrdersRefundByOrderId(orderId));

        //then - verify the output
        assertNotNull(notFoundException);
        assertEquals(expectedExceptionMessage, notFoundException.getMessage());

        verify(orderRepository).findById(orderId);
    }
}