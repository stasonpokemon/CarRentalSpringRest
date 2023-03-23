package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.RegistrationController;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @Override
    public ResponseEntity<?> saveRegisteredUser(CreateUserRequestDTO createUserRequestDTO) {
        return userService.saveRegisteredUser(createUserRequestDTO);
    }

    @Override
    public ResponseEntity<?> activateUser(String activateCode) {
        return userService.activateUser(activateCode);
    }
}
