package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.CarController;
import com.spring.rest.api.entity.dto.request.CreatCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import com.spring.rest.api.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CarControllerImpl implements CarController {

    private final CarService carService;

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("GET request to find all cars");

        return carService.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> findAllNotMarkedAsDeleted(Pageable pageable) {

        log.info("GET request to find all cars not marked as deleted");

        return carService.findAllNotMarkAsDeleted(pageable);
    }

    @Override
    public ResponseEntity<?> findCar(Long carId) {

        log.info("GET request to find cars with id: {}", carId);

        return carService.findById(carId);
    }

    @Override
    public ResponseEntity<?> createCar(CreatCarRequestDTO creatCarRequestDTO) {

        log.info("POST request to create car: {}", creatCarRequestDTO);

        return carService.save(creatCarRequestDTO);
    }

    @Override
    public ResponseEntity<?> updateCar(Long carId,
                                       UpdateCarRequestDTO updateCarRequestDTO) {

        log.info("PATCH request to update car: {} with id: {}", updateCarRequestDTO, carId);

        return carService.update(carId, updateCarRequestDTO);
    }

    @Override
    public ResponseEntity<?> fixBrokenCar(Long carId) {

        log.info("PATCH request to fix broken car with id: {}", carId);

        return carService.fixBrokenCar(carId);
    }

    @Override
    public ResponseEntity<String> markCarAsDeleted(Long carId) {

        log.info("PATCH request to mark car with id: {} as deleted ", carId);

        return carService.markCarAsDeleted(carId);
    }
}
