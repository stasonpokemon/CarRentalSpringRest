package com.spring.rest.api.controller;

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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") Long userId) {
        return userService.findUser(userId);
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable("id") Long userId) {
        return userService.blockUser(userId);
    }

    @PatchMapping("/{id}/unlock")
    public ResponseEntity<?> unlockUser(@PathVariable("id") Long userId) {
        return userService.unlockUser(userId);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> findUsersOrders(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                                             @PathVariable("id") Long userId) {
        return orderService.findOrdersByUserId(userId, pageable);
    }

    @GetMapping("/{id}/passport")
    public ResponseEntity<?> findUsersPassport(@PathVariable("id") Long userId) {
        return userService.findPassportByUserId(userId);
    }

    @PostMapping("/{id}/passport")
    public ResponseEntity<?> createPassportForUser(@PathVariable("id") Long userId,
                                                   @RequestBody @Valid PassportDTO passportDTO,
                                                   BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return userService.createPassportForUser(userId, passportDTO);
    }

    @PatchMapping("/{id}/passport")
    public ResponseEntity<?> editUsersPassport(@PathVariable("id") Long userId,
                                               @RequestBody PassportDTO passportDTO) {
        return userService.updateUsersPassport(userId, passportDTO);
    }

}
