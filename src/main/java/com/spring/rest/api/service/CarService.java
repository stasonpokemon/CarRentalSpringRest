package com.spring.rest.api.service;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface CarService {


    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> findAll(String[] sort);

    ResponseEntity<?> findAllNotMarkAsDeleted(String[] sort);

    ResponseEntity<?> findAllFreeNotMarkAsDeleted(String[] sort);

    ResponseEntity<?> save(CarDTO carDTO);

    ResponseEntity<?> update(Long id, CarDTO carDTO);

    ResponseEntity<String> delete(Long id);

    ResponseEntity<String> markCarAsDeleted(Long id);

    Car findCarByIdOrThrowException(Long id);
}
