package com.spring.rest.api.service.impl;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import com.spring.rest.api.entity.mapper.CarMapper;
import com.spring.rest.api.exception.BadRequestException;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.CarRepository;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.util.CarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(UUID id) {

        log.info("Finding car with id: {}", id);

        CarResponseDTO carResponseDTO = carMapper.carToCarResponseDTO(findCarByIdOrThrowException(id));
        ResponseEntity<?> response = new ResponseEntity<>(carResponseDTO, HttpStatus.OK);

        log.info("Find car: {}", carResponseDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("Finding all cars");

        List<CarResponseDTO> carsDTO = carRepository.findAll(pageable)
                .stream()
                .map(carMapper::carToCarResponseDTO)
                .collect(Collectors.toList());

        if (carsDTO.isEmpty()) {
            throw new NotFoundException(Car.class);
        }

        ResponseEntity<?> response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);

        log.info("Find all cars: {}", carsDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllNotMarkAsDeleted(Pageable pageable) {

        log.info("Finding all not mark as deleted cars");

        List<CarResponseDTO> carsDTO = carRepository.findAllByDeleted(false, pageable)
                .stream()
                .map(carMapper::carToCarResponseDTO)
                .collect(Collectors.toList());

        if (carsDTO.isEmpty()) {
            throw new NotFoundException("There isn't cars not mark as deleted");
        }

        ResponseEntity<?> response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);

        log.info("Find all not mark as deleted cars: {}", carsDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllFreeNotMarkAsDeleted(Pageable pageable) {

        log.info("Finding all free not mark as deleted cars");

        List<CarResponseDTO> carsDTO = carRepository.findAllByBusyAndDeleted(false, false, pageable)
                .stream()
                .map(carMapper::carToCarResponseDTO)
                .collect(Collectors.toList());

        if (carsDTO.isEmpty()) {
            throw new NotFoundException("There isn't free cars not mark as deleted");
        }

        ResponseEntity<?> response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);

        log.info("Find all free not mark as deleted cars: {}", carsDTO);

        return response;
    }

    @Override
    public ResponseEntity<?> save(CreateCarRequestDTO createCarRequestDTO) {

        log.info("Saving car: {}", createCarRequestDTO);

        Car car = carMapper.createCarRequestDTOToCar(createCarRequestDTO);
        car.setDamageStatus("Without damage");
        car.setBusy(false);
        car.setDeleted(false);
        Car savedCar = carRepository.save(car);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarResponseDTO(savedCar), HttpStatus.OK);

        log.info("Save car: {}", car);

        return response;
    }

    @Override
    public ResponseEntity<?> update(UUID id,
                                    UpdateCarRequestDTO updateCarRequestDTO) {

        log.info("Updating car: {} with id: {}", updateCarRequestDTO, id);

        Car car = findCarByIdOrThrowException(id);
        CarUtil.getInstance().copyNotNullFieldsFromUpdateCarDTOToCar(updateCarRequestDTO, car);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Update car: {} with id: {}", car, id);

        return response;
    }

    @Override
    public ResponseEntity<?> fixBrokenCar(UUID carId) {

        log.info("Fixing broken car with id: {}", carId);

        Car car = findCarByIdOrThrowException(carId);

        if (car.isDeleted()) {
            return new ResponseEntity<>(String.format("Unable to fix car. Car with id = %s is deleted", carId), HttpStatus.OK);
        }

        if (!car.isBroken()) {
            return new ResponseEntity<>(String.format("Unable to fix car. Car with id = %s already fixed", carId), HttpStatus.OK);
        }

        car.setBroken(false);
        car.setDamageStatus("Without damage");
        car.setBusy(false);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Fix broken car: {} with id: {}", car, carId);

        return response;

    }

    @Override
    public ResponseEntity<?> setCarAsBroken(UUID carId,
                                            String damageStatus) {

        log.info("Setting the car as broken with id: {} and damage description: {}", carId, damageStatus);

        Car car = findCarByIdOrThrowException(carId);

        if (car.isBroken()) {
            throw new BadRequestException(String.format("Car with id = %s is already broken", carId));
        }

        if (car.isBusy()) {
            throw new BadRequestException(String.format("Car with id = %s is busy now", carId));
        }

        if (car.isDeleted()){
            throw new BadRequestException(String.format("Car with id = %s is deleted", carId));
        }

        car.setBroken(true);
        car.setDamageStatus(damageStatus);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Set the car: {} as broken with id: {}", car, carId);
        return response;
    }


    @Override
    public ResponseEntity<?> markCarAsDeleted(UUID id) {

        log.info("Marking car with id: {} as deleted", id);

        Car car = findCarByIdOrThrowException(id);

        if (car.isDeleted()) {
            return new ResponseEntity<>(String.format("Car with id = %s already marked as deleted", id), HttpStatus.OK);
        }

        car.setDeleted(true);
        car.setBusy(true);

        ResponseEntity<CarResponseDTO> response = new ResponseEntity<>(carMapper.carToCarResponseDTO(car), HttpStatus.OK);

        log.info("Mark car: {} with id: {} as deleted", car, id);

        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Car findCarByIdOrThrowException(UUID id) {

        log.info("Finding car with id: {}", id);

        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException(
                Car.class, id));

        log.info("Find car: {} with id: {}", car, id);

        return car;
    }


}
