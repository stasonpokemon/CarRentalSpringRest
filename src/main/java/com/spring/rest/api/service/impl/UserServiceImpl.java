package com.spring.rest.api.service.impl;

import com.spring.rest.api.repo.UserRepository;
import com.spring.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
}
