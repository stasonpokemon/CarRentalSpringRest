package com.spring.rest.api.service;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CarService {


    ResponseEntity<?> findById(Long carId);

    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> findAllNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<?> findAllFreeNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<?> save(CarDTO carDTO);

    ResponseEntity<?> update(Long carId, CarDTO carDTO);

    ResponseEntity<String> markCarAsDeleted(Long carId);

    Car findCarByIdOrThrowException(Long carId);

    ResponseEntity<?> fixBrokenCar(Long carId);
}
