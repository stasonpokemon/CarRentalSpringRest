package com.spring.rest.api.service;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface CarService {


    ResponseEntity<?> findById(Long carId);

    ResponseEntity<?> findAll(String[] sort);

    ResponseEntity<?> findAllNotMarkAsDeleted(String[] sort);

    ResponseEntity<?> findAllFreeNotMarkAsDeleted(String[] sort);

    ResponseEntity<?> save(CarDTO carDTO);

    ResponseEntity<?> update(Long carId, CarDTO carDTO);

    ResponseEntity<String> markCarAsDeleted(Long carId);

    Car findCarByIdOrThrowException(Long carId);

    ResponseEntity<?> fixBrokenCar(Long carId);
}
