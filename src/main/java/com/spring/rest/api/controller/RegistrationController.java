package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/registration")
public interface RegistrationController {

    @PostMapping()
    ResponseEntity<?> saveRegisteredUser(@RequestBody @Valid UserDTO userDTO,
                                         BindingResult bindingResult);

    @GetMapping("/activate/{code}")
    ResponseEntity<?> activateUser(@PathVariable("code") String activateCode);
}
