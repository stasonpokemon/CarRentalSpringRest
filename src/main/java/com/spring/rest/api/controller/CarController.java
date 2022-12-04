package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.util.CarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@RequestParam(name = "sort", defaultValue = "id,asc", required = false) String[] sort) {
        return carService.findAll(sort);
    }

    @GetMapping
    public ResponseEntity<?> findAllNotMarkedAsDeleted(@RequestParam(name = "sort", defaultValue = "id,asc", required = false) String[] sort) {
        return carService.findAllNotMarkAsDeleted(sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCar(@PathVariable(name = "id") Long id) {
        return carService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createCar(@RequestBody @Valid CarDTO carDTO,
                                       BindingResult bindingResult) {
        CarUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return carService.save(carDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") Long id,
                                       @RequestBody CarDTO carDTO) {
        return carService.update(id, carDTO);
    }

    @PatchMapping("/{id}/remove")
    public ResponseEntity<String> markCarAsDeleted(@PathVariable("id") Long id) {
        return carService.markCarAsDeleted(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFromDB(@PathVariable("id") Long id) {
        return carService.delete(id);
    }
}
