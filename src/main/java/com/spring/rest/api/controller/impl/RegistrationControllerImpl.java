package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.RegistrationController;
import com.spring.rest.api.entity.dto.request.CreateUserRequestDTO;
import com.spring.rest.api.entity.dto.response.UserResponseDTO;
import com.spring.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for RegistrationController.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDTO> saveRegisteredUser(CreateUserRequestDTO createUserRequestDTO) {

        log.info("POST request to save registered user: {}", createUserRequestDTO);

        return userService.saveRegisteredUser(createUserRequestDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> activateUser(String activateCode) {

        log.info("GET request to activate user");

        return userService.activateUser(activateCode);
    }
}
