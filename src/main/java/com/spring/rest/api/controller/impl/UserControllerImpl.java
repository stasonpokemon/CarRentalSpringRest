package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.UserController;
import com.spring.rest.api.entity.dto.request.PassportRequestDTO;
import com.spring.rest.api.entity.dto.response.OrderResponseDTO;
import com.spring.rest.api.entity.dto.response.PassportResponseDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final OrderService orderService;

    @Override
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable) {

        log.info("GET request to find all users");

        return userService.findAll(pageable);
    }

    @Override
    public ResponseEntity<UserResponseDTO> findUserById(UUID userId) {

        log.info("GET request to find user with id: {}", userId);

        return userService.findUser(userId);
    }

    @Override
    public ResponseEntity<UserResponseDTO> blockUser(UUID userId) {

        log.info("PATCH request to block user with id: {}", userId);

        return userService.blockUser(userId);
    }

    @Override
    public ResponseEntity<UserResponseDTO> unlockUser(UUID userId) {

        log.info("PATCH request to unlock user with id: {}", userId);

        return userService.unlockUser(userId);
    }

    @Override
    public ResponseEntity<Page<OrderResponseDTO>> findUsersOrders(Pageable pageable,
                                                                  UUID userId) {

        log.info("GET request to find user's orders by userId: {}", userId);

        return orderService.findOrdersByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<PassportResponseDTO> findUsersPassport(UUID userId) {

        log.info("GET request to find user's passport by userId: {}", userId);

        return userService.findPassportByUserId(userId);
    }

    @Override
    public ResponseEntity<PassportResponseDTO> createPassportForUser(UUID userId,
                                                   PassportRequestDTO passportRequestDTO) {

        log.info("POST request to create passport: {} for user with id: {}", passportRequestDTO, userId);

        return userService.createPassportForUser(userId, passportRequestDTO);
    }

    @Override
    public ResponseEntity<PassportResponseDTO> editUsersPassport(UUID userId,
                                               PassportRequestDTO passportRequestDTO) {

        log.info("PATCH request to change passport: {} of user with userId: {}", passportRequestDTO, userId);

        return userService.updateUsersPassport(userId, passportRequestDTO);
    }

}
