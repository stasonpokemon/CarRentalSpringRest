package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.UserController;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final OrderService orderService;

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findUserById(Long userId) {
        return userService.findUser(userId);
    }

    @Override
    public ResponseEntity<?> blockUser(Long userId) {
        return userService.blockUser(userId);
    }

    @Override
    public ResponseEntity<?> unlockUser(Long userId) {
        return userService.unlockUser(userId);
    }

    @Override
    public ResponseEntity<?> findUsersOrders(Pageable pageable,
                                             Long userId) {
        return orderService.findOrdersByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<?> findUsersPassport(Long userId) {
        return userService.findPassportByUserId(userId);
    }

    @Override
    public ResponseEntity<?> createPassportForUser(Long userId,
                                                   PassportDTO passportDTO) {
        return userService.createPassportForUser(userId, passportDTO);
    }

    @Override
    public ResponseEntity<?> editUsersPassport(Long userId,
                                               PassportDTO passportDTO) {
        return userService.updateUsersPassport(userId, passportDTO);
    }

}
