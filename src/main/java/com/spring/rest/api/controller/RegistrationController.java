package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/registration")
public interface RegistrationController {

    @PostMapping()
    ResponseEntity<?> saveRegisteredUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO);

    @GetMapping("/activate/{code}")
    ResponseEntity<?> activateUser(
            @PathVariable("code") String activateCode);
}
