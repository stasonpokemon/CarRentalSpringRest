package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.UserDTO;
import com.spring.rest.api.service.UserService;
import com.spring.rest.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> saveRegisteredUser(@RequestBody @Valid UserDTO userDTO,
                                                BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return userService.saveRegisteredUser(userDTO);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activateUser(@PathVariable("code") String activateCode) {
        return userService.activateUser(activateCode);
    }
}
