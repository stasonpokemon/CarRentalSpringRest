package com.spring.rest.api.service.impl;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.exception.EntityNotFoundException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import com.spring.rest.api.repo.CarRepository;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.util.CarUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) {
        ResponseEntity<?> response;
        try {
            CarDTO carDTO = modelMapper.map(findCarByIdOrThrowException(id), CarDTO.class);
            response = new ResponseEntity<>(carDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to get car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) {
        ResponseEntity<?> response;
        try {
            List<CarDTO> carsDTO = carRepository.findAll(pageable).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());

            if (carsDTO.isEmpty()) {
                return new ResponseEntity<>("There isn't cars", HttpStatus.OK);
            }
            response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to get all cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllNotMarkAsDeleted(Pageable pageable) {
        ResponseEntity<?> response;
        try {
            List<CarDTO> carsDTO = carRepository.findAllByDeleted(false, pageable).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            if (carsDTO.isEmpty()) {
                return new ResponseEntity<>("There isn't cars not mark as deleted", HttpStatus.OK);
            }
            response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to get all not mark as deleted cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllFreeNotMarkAsDeleted(Pageable pageable) {
        ResponseEntity<?> response;
        try {
            List<CarDTO> carsDTO = carRepository.findAllByEmploymentStatusAndDeleted(true, false, pageable).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            if (carsDTO.isEmpty()) {
                return new ResponseEntity<>("There isn't free cars not mark as deleted", HttpStatus.OK);
            }
            response = new ResponseEntity<>(new PageImpl<>(carsDTO), HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to get all free and not mark as deleted cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> save(CarDTO carDTO) {
        ResponseEntity<?> response;
        try {
            Car car = modelMapper.map(carDTO, Car.class);
            car.setDeleted(false);
            Car savedCar = carRepository.save(car);
            response = new ResponseEntity<>(modelMapper.map(savedCar, CarDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to create car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> update(Long id, CarDTO carDTO) {
        ResponseEntity<?> response;
        try {
            Car car = findCarByIdOrThrowException(id);
            CarUtil.getInstance().copyNotNullFieldsFromCarDTOToCar(carDTO, car);
            response = new ResponseEntity<>(modelMapper.map(car, CarDTO.class), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to update car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> fixBrokenCar(Long carId) {
        ResponseEntity<?> response;
        try {
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
            response = new ResponseEntity<>(modelMapper.map(car, CarDTO.class), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to fix car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }


    @Override
    public ResponseEntity<String> markCarAsDeleted(Long id) {
        ResponseEntity<String> response;
        try {
            Car car = findCarByIdOrThrowException(id);
            if (car.isDeleted()) {
                response = new ResponseEntity<>(String.format("Car with id = %s already marked as deleted", id), HttpStatus.OK);
                return response;
            }
            car.setDeleted(true);
            car.setEmploymentStatus(false);
            response = new ResponseEntity<>(String.format("Car with id = %s was marked as deleted", id), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<>("Unable to mark car as deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Car findCarByIdOrThrowException(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Car with id = %s not found", id)));
    }


}
