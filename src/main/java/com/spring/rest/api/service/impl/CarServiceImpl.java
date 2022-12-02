package com.spring.rest.api.service.impl;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.exception.CarNotFoundException;
import com.spring.rest.api.repo.CarRepository;
import com.spring.rest.api.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> findById(Long id) {
        ResponseEntity<?> response;
        try {
            CarDTO carDTO = modelMapper.map(findCarByIdOrThrowException(id), CarDTO.class);
            response = new ResponseEntity<CarDTO>(carDTO, HttpStatus.OK);
        } catch (CarNotFoundException carNotFoundException) {
            throw carNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> findAll() {
        ResponseEntity<?> response;
        try {
            List<CarDTO> carsDTO = carRepository.findAll().stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            response = new ResponseEntity<List<CarDTO>>(carsDTO, HttpStatus.OK);
        } catch (CarNotFoundException carNotFoundException) {
            throw carNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get all cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> findAllNotMarkAsDeleted() {
        ResponseEntity<?> response;
        try {
            List<CarDTO> carsDTO = carRepository.findAllByDeleted(false).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            response = new ResponseEntity<List<CarDTO>>(carsDTO, HttpStatus.OK);
        } catch (CarNotFoundException carNotFoundException) {
            throw carNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get all not mark as deleted cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> save(CarDTO carDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long id, CarDTO carDTO) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        ResponseEntity<String> response;
        try {
            carRepository.delete(findCarByIdOrThrowException(id));
            response = new ResponseEntity<String>(new StringBuilder()
                    .append("Car with id = ")
                    .append(id)
                    .append(" was deleted").toString(), HttpStatus.OK);
        } catch (CarNotFoundException carNotFoundException){
            throw carNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to delete car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> setIsDeletedTrue(Long id) {
        ResponseEntity<String> response;
        try {
            Car car = findCarByIdOrThrowException(id);
            car .setDeleted(true);
            car.setUpdatedAt(LocalDateTime.now());
            response = new ResponseEntity<String>(new StringBuilder()
                    .append("Car with id = ")
                    .append(id)
                    .append(" was marked as deleted").toString(), HttpStatus.OK);
        } catch (CarNotFoundException carNotFoundException){
            throw carNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to mark car as deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    private Car findCarByIdOrThrowException(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(new StringBuilder()
                .append("Car with id = ")
                .append(id)
                .append(" not found").toString()));
    }

}
