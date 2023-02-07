package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return carService.findAll(pageable);
    }

    @GetMapping
    public ResponseEntity<?> findAllNotMarkedAsDeleted(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return carService.findAllNotMarkAsDeleted(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCar(@PathVariable(name = "id") Long carId) {
        return carService.findById(carId);
    }

    @PostMapping
    public ResponseEntity<?> createCar(@RequestBody @Valid CarDTO carDTO,
                                       BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return carService.save(carDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable("id") Long carId,
                                       @RequestBody CarDTO carDTO) {
        return carService.update(carId, carDTO);
    }

    @PatchMapping("/{id}/fix")
    public ResponseEntity<?> fixBrokenCar(@PathVariable("id") Long carId) {
        return carService.fixBrokenCar(carId);
    }

    @PatchMapping("/{id}/remove")
    public ResponseEntity<String> markCarAsDeleted(@PathVariable("id") Long carId) {
        return carService.markCarAsDeleted(carId);
    }
}
