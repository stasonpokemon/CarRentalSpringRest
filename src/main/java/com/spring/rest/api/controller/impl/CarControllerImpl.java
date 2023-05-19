package com.spring.rest.api.controller.impl;

import com.spring.rest.api.controller.CarController;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import com.spring.rest.api.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * Implementation class for CarController.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class CarControllerImpl implements CarController {

    private final CarService carService;

    @Override
    public ResponseEntity<Page<CarResponseDTO>> findAll(Pageable pageable) {

        log.info("GET request to find all cars");

        return carService.findAll(pageable);
    }

    @Override
    public ResponseEntity<Page<CarResponseDTO>> findAllNotMarkedAsDeleted(Pageable pageable) {

        log.info("GET request to find all cars not marked as deleted");

        return carService.findAllNotMarkAsDeleted(pageable);
    }

    @Override
    public ResponseEntity<CarResponseDTO> findCar(UUID carId) {

        log.info("GET request to find cars with id: {}", carId);

        return carService.findById(carId);
    }

    @Override
    public ResponseEntity<CarResponseDTO> createCar(CreateCarRequestDTO createCarRequestDTO) {

        log.info("POST request to create car: {}", createCarRequestDTO);

        return carService.save(createCarRequestDTO);
    }

    @Override
    public ResponseEntity<CarResponseDTO> updateCar(UUID carId,
                                       UpdateCarRequestDTO updateCarRequestDTO) {

        log.info("PATCH request to update car: {} with id: {}", updateCarRequestDTO, carId);

        return carService.update(carId, updateCarRequestDTO);
    }

    @Override
    public ResponseEntity<CarResponseDTO> fixBrokenCar(UUID carId) {

        log.info("PATCH request to fix broken car with id: {}", carId);

        return carService.fixBrokenCar(carId);
    }

    @Override
    public ResponseEntity<CarResponseDTO> setCarAsBroken(UUID carId,
                                            String damageStatus) {

        log.info("PATCH request to set the car as broken with car's id: {} and damage description: {}",
                carId, damageStatus);

        return carService.setCarAsBroken(carId, damageStatus);
    }

    @Override
    public ResponseEntity<CarResponseDTO> markCarAsDeleted(UUID carId) {

        log.info("PATCH request to mark car with id: {} as deleted ", carId);

        return carService.markCarAsDeleted(carId);
    }
}

