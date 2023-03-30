package com.spring.rest.api.service;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CarService {


    ResponseEntity<?> findById(UUID carId);

    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> findAllNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<?> findAllFreeNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<?> save(CreateCarRequestDTO createCarRequestDTO);

    ResponseEntity<?> update(UUID carId,
                             UpdateCarRequestDTO updateCarRequestDTO);

    ResponseEntity<?> markCarAsDeleted(UUID carId);

    Car findCarByIdOrThrowException(UUID carId);

    ResponseEntity<?> fixBrokenCar(UUID carId);

    ResponseEntity<?> setCarAsBroken(UUID carId,
                                     String damageStatus);
}
