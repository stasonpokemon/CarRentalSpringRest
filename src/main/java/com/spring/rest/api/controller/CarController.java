package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> findAllNotMarkedAsDeleted(){
        return carService.findAllNotMarkAsDeleted();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable(name = "id") Long id){
        return carService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createCar(@RequestBody CarDTO carDTO){
        return carService.save(carDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") Long id,
                                       @RequestBody CarDTO carDTO){
      return carService.update(id, carDTO);
    }

    @PatchMapping("/{id}/remove")
    public ResponseEntity<String> markCarAsDeleted(@PathVariable("id") Long id){
        return carService.markCarAsDeleted(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFromDB(@PathVariable("id") Long id){
        return carService.delete(id);
    }
}
