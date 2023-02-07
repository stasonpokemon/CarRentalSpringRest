package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.UserController;
import com.spring.rest.api.entity.dto.PassportDTO;
import com.spring.rest.api.service.OrderService;
import com.spring.rest.api.service.UserService;
import com.spring.rest.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final OrderService orderService;

    @Autowired
    public UserControllerImpl(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<?> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findUserById(@PathVariable("id") Long userId) {
        return userService.findUser(userId);
    }

    @Override
    public ResponseEntity<?> blockUser(@PathVariable("id") Long userId) {
        return userService.blockUser(userId);
    }

    @Override
    public ResponseEntity<?> unlockUser(@PathVariable("id") Long userId) {
        return userService.unlockUser(userId);
    }

    @Override
    public ResponseEntity<?> findUsersOrders(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                                             @PathVariable("id") Long userId) {
        return orderService.findOrdersByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity<?> findUsersPassport(@PathVariable("id") Long userId) {
        return userService.findPassportByUserId(userId);
    }

    @Override
    public ResponseEntity<?> createPassportForUser(@PathVariable("id") Long userId,
                                                   @RequestBody @Valid PassportDTO passportDTO,
                                                   BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return userService.createPassportForUser(userId, passportDTO);
    }

    @Override
    public ResponseEntity<?> editUsersPassport(@PathVariable("id") Long userId,
                                               @RequestBody PassportDTO passportDTO) {
        return userService.updateUsersPassport(userId, passportDTO);
    }

}
