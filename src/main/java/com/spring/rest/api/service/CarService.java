package com.spring.rest.api.service;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreatOrUpdateCarRequestDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CarService {


    ResponseEntity<?> findById(Long carId);

    ResponseEntity<?> findAll(Pageable pageable);

    ResponseEntity<?> findAllNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<?> findAllFreeNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<?> save(CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO);

    ResponseEntity<?> update(Long carId, CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO);

    ResponseEntity<String> markCarAsDeleted(Long carId);

    Car findCarByIdOrThrowException(Long carId);

    ResponseEntity<?> fixBrokenCar(Long carId);
}
