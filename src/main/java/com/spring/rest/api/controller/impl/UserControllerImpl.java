package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.UserController;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final OrderService orderService;

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("GET request to find all users");

        return userService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findUserById(Long userId) {

        log.info("GET request to find user with id: {}", userId);

        return userService.findUser(userId);
    }

    @Override
    public ResponseEntity<?> blockUser(Long userId) {

        log.info("PATCH request to block user with id: {}", userId);

        return userService.blockUser(userId);
    }

    @Override
    public ResponseEntity<?> unlockUser(Long userId) {

        log.info("PATCH request to unlock user with id: {}", userId);

        return userService.unlockUser(userId);
    }

    @Override
    public ResponseEntity<?> findUsersOrders(Pageable pageable,
                                             Long userId) {

        log.info("GET request to find user's orders by userId: {}", userId);

        return orderService.findOrdersByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<?> findUsersPassport(Long userId) {

        log.info("GET request to find user's passport by userId: {}", userId);

        return userService.findPassportByUserId(userId);
    }

    @Override
    public ResponseEntity<?> createPassportForUser(Long userId,
                                                   PassportDTO passportDTO) {

        log.info("POST request to create passport: {} for user with id: {}", passportDTO, userId);

        return userService.createPassportForUser(userId, passportDTO);
    }

    @Override
    public ResponseEntity<?> editUsersPassport(Long userId,
                                               PassportDTO passportDTO) {

        log.info("PATCH request to change passport: {} of user with userId: {}", passportDTO, userId);

        return userService.updateUsersPassport(userId, passportDTO);
    }

}
