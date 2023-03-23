package com.spring.rest.api.service.impl;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.entity.mapper.CarMapper;
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
    public ResponseEntity<?> findById(Long id) {

        log.info("Finding car with id: {}", id);

        CarDTO carDTO = carMapper.carToCarDTO(findCarByIdOrThrowException(id));
        ResponseEntity<?> response = new ResponseEntity<>(carDTO, HttpStatus.OK);

        log.info("Find car: {}", carDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) {

        log.info("Finding all cars");

        List<CarDTO> carsDTO = carRepository.findAll(pageable)
                .stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());

        if (carsDTO.isEmpty()) {
            return new ResponseEntity<>("There isn't cars", HttpStatus.OK);
        }

        ResponseEntity<?> response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);

        log.info("Find all cars: {}", carsDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllNotMarkAsDeleted(Pageable pageable) {

        log.info("Finding all not mark as deleted cars");

        List<CarDTO> carsDTO = carRepository.findAllByDeleted(false, pageable)
                .stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());

        if (carsDTO.isEmpty()) {
            return new ResponseEntity<>("There isn't cars not mark as deleted", HttpStatus.OK);
        }

        ResponseEntity<?> response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);

        log.info("Find all not mark as deleted cars: {}", carsDTO);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllFreeNotMarkAsDeleted(Pageable pageable) {

        log.info("Finding all free not mark as deleted cars");

        List<CarDTO> carsDTO = carRepository.findAllByEmploymentStatusAndDeleted(true, false, pageable)
                .stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());

        if (carsDTO.isEmpty()) {
            return new ResponseEntity<>("There isn't free cars not mark as deleted", HttpStatus.OK);
        }

        ResponseEntity<?> response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);

        log.info("Find all free not mark as deleted cars: {}", carsDTO);

        return response;
    }

    @Override
    public ResponseEntity<?> save(CarDTO carDTO) {

        log.info("Saving car: {}", carDTO);

        Car car = carMapper.carDTOToCar(carDTO);
        car.setDeleted(false);
        Car savedCar = carRepository.save(car);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarDTO(savedCar), HttpStatus.OK);

        log.info("Save car: {}", car);

        return response;
    }

    @Override
    public ResponseEntity<?> update(Long id, CarDTO carDTO) {

        log.info("Updating car: {} with id: {}", carDTO, id);

        Car car = findCarByIdOrThrowException(id);
        CarUtil.getInstance().copyNotNullFieldsFromCarDTOToCar(carDTO, car);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarDTO(car), HttpStatus.OK);

        log.info("Update car: {} with id: {}", car, id);

        return response;
    }

    @Override
    public ResponseEntity<?> fixBrokenCar(Long carId) {

        log.info("Fixing broken car with id: {}", carId);

        Car car = findCarByIdOrThrowException(carId);

        if (car.isDeleted()) {
            return new ResponseEntity<>(String.format("Unable to fix car. Car with id = %s is deleted", carId), HttpStatus.OK);
        }

        if (!car.isBroken()) {
            return new ResponseEntity<>(String.format("Unable to fix car. Car with id = %s already fixed", carId), HttpStatus.OK);
        }

        car.setBroken(false);
        car.setDamageStatus("");
        car.setEmploymentStatus(true);

        ResponseEntity<?> response = new ResponseEntity<>(carMapper.carToCarDTO(car), HttpStatus.OK);

        log.info("Fix broken car: {} with id: {}", car, carId);

        return response;

    }


    @Override
    public ResponseEntity<String> markCarAsDeleted(Long id) {

        log.info("Marking car with id: {} as deleted", id);

        Car car = findCarByIdOrThrowException(id);

        if (car.isDeleted()) {
            return new ResponseEntity<>(String.format("Car with id = %s already marked as deleted", id), HttpStatus.OK);
        }

        car.setDeleted(true);
        car.setEmploymentStatus(false);

        ResponseEntity<String> response = new ResponseEntity<>(String.format("Car with id = %s was marked as deleted", id), HttpStatus.OK);

        log.info("Mark car: {} with id: {} as deleted", car, id);

        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Car findCarByIdOrThrowException(Long id) {

        log.info("Finding car with id: {}", id);

        Car car = carRepository.findById(id).orElseThrow(() -> new NotFoundException(
                Car.class, id));

        log.info("Find car: {} with id: {}", car, id);

        return car;
    }


}
