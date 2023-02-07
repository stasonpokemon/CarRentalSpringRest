package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.CarController;
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
public class CarControllerImpl implements CarController {

    private final CarService carService;

    @Autowired
    public CarControllerImpl(CarService carService) {
        this.carService = carService;
    }

    @Override
    public ResponseEntity<?> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return carService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findAllNotMarkedAsDeleted(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return carService.findAllNotMarkAsDeleted(pageable);
    }

    @Override
    public ResponseEntity<?> findCar(@PathVariable(name = "id") Long carId) {
        return carService.findById(carId);
    }

    @Override
    public ResponseEntity<?> createCar(@RequestBody @Valid CarDTO carDTO,
                                       BindingResult bindingResult) {
        CommonUtil.getInstance().checkBindingResultOrThrowException(bindingResult);
        return carService.save(carDTO);
    }

    @Override
    public ResponseEntity<?> updateCar(@PathVariable("id") Long carId,
                                       @RequestBody CarDTO carDTO) {
        return carService.update(carId, carDTO);
    }

    @Override
    public ResponseEntity<?> fixBrokenCar(@PathVariable("id") Long carId) {
        return carService.fixBrokenCar(carId);
    }

    @Override
    public ResponseEntity<String> markCarAsDeleted(@PathVariable("id") Long carId) {
        return carService.markCarAsDeleted(carId);
    }
}
