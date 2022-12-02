package com.spring.rest.api.controller;

import com.spring.rest.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> getCars(){
        return carService.findAllNotMarkAsDeleted();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable(name = "id") Long id){
        return carService.findById(id);
    }
}
