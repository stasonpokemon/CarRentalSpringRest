package com.spring.rest.api.service.impl;

import com.spring.rest.api.repo.OrderRepository;
import com.spring.rest.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
}
