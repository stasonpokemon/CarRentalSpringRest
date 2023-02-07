package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.RegistrationController;
import com.spring.rest.api.entity.dto.UserDTO;
import com.spring.rest.api.service.UserService;
import com.spring.rest.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> saveRegisteredUser(@RequestBody @Valid UserDTO userDTO,
                                                BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return userService.saveRegisteredUser(userDTO);
    }

    @Override
    public ResponseEntity<?> activateUser(@PathVariable("code") String activateCode) {
        return userService.activateUser(activateCode);
    }
}
