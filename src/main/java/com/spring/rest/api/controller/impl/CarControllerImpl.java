package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.CarController;
import com.spring.rest.api.entity.dto.request.CreatOrUpdateCarRequestDTO;
import com.spring.rest.api.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CarControllerImpl implements CarController {

    private final CarService carService;

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {
        return carService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findAllNotMarkedAsDeleted(Pageable pageable) {
        return carService.findAllNotMarkAsDeleted(pageable);
    }

    @Override
    public ResponseEntity<?> findCar(Long carId) {
        return carService.findById(carId);
    }

    @Override
    public ResponseEntity<?> createCar(CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO) {
        return carService.save(creatOrUpdateCarRequestDTO);
    }

    @Override
    public ResponseEntity<?> updateCar(Long carId,
                                       CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO) {
        return carService.update(carId, creatOrUpdateCarRequestDTO);
    }

    @Override
    public ResponseEntity<?> fixBrokenCar(Long carId) {
        return carService.fixBrokenCar(carId);
    }

    @Override
    public ResponseEntity<String> markCarAsDeleted(Long carId) {
        return carService.markCarAsDeleted(carId);
    }
}
