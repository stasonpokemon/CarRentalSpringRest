package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.PassportDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RequestMapping("/users")
public interface UserController {

    @GetMapping
    ResponseEntity<?> findAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<?> findUserById(
            @PathVariable("id") Long userId);

    @PatchMapping("/{id}/block")
    ResponseEntity<?> blockUser(
            @PathVariable("id") Long userId);

    @PatchMapping("/{id}/unlock")
    ResponseEntity<?> unlockUser(
            @PathVariable("id") Long userId);

    @GetMapping("/{id}/orders")
    ResponseEntity<?> findUsersOrders(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable("id") Long userId);

    @GetMapping("/{id}/passport")
    ResponseEntity<?> findUsersPassport(
            @PathVariable("id") Long userId);

    @PostMapping("/{id}/passport")
    ResponseEntity<?> createPassportForUser(
            @PathVariable("id") Long userId,
            @RequestBody @Valid PassportDTO passportDTO);

    @PatchMapping("/{id}/passport")
    ResponseEntity<?> editUsersPassport(
            @PathVariable("id") Long userId,
            @RequestBody PassportDTO passportDTO);

}
