package com.spring.rest.api.service;


import com.spring.rest.api.entity.dto.CarDTO;
import org.springframework.http.ResponseEntity;

public interface CarService {


    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> findAll();

    ResponseEntity<?> findAllNotMarkAsDeleted();

    ResponseEntity<?> save(CarDTO carDTO);

    ResponseEntity<?> update(Long id, CarDTO carDTO);

    ResponseEntity<String> delete(Long id);

    ResponseEntity<String> setIsDeletedTrue(Long id);


}
