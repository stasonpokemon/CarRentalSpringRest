package com.spring.rest.api.service;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CarService {


    ResponseEntity<CarResponseDTO> findById(UUID carId);

    ResponseEntity<Page<CarResponseDTO>> findAll(Pageable pageable);

    ResponseEntity<Page<CarResponseDTO>> findAllNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<Page<CarResponseDTO>> findAllFreeNotMarkAsDeleted(Pageable pageable);

    ResponseEntity<CarResponseDTO> save(CreateCarRequestDTO createCarRequestDTO);

    ResponseEntity<CarResponseDTO> update(UUID carId,
                                          UpdateCarRequestDTO updateCarRequestDTO);

    ResponseEntity<CarResponseDTO> markCarAsDeleted(UUID carId);

    Car findCarByIdOrThrowException(UUID carId);

    ResponseEntity<CarResponseDTO> fixBrokenCar(UUID carId);

    ResponseEntity<CarResponseDTO> setCarAsBroken(UUID carId, String damageStatus);
}
