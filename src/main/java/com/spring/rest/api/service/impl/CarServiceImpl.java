package com.spring.rest.api.service.impl;


import com.spring.rest.api.repo.CarRepository;
import com.spring.rest.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;
}
