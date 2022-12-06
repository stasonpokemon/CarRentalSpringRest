package com.spring.rest.api.service.impl;


import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import com.spring.rest.api.exception.EntityNotFoundException;
import com.spring.rest.api.exception.SortParametersNotValidException;
import com.spring.rest.api.repo.CarRepository;
import com.spring.rest.api.service.CarService;
import com.spring.rest.api.util.CarUtil;
import com.spring.rest.api.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) {
        ResponseEntity<?> response;
        try {
            CarDTO carDTO = modelMapper.map(findCarByIdOrThrowException(id), CarDTO.class);
            response = new ResponseEntity<CarDTO>(carDTO, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(String[] sort) {
        ResponseEntity<?> response;
        try {
            List<Order> orders = CommonUtil.getInstance().getOrdersFromRequest(sort, Car.class);
            List<CarDTO> carsDTO = carRepository.findAll(Sort.by(orders)).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            if (carsDTO.isEmpty()) {
                response = new ResponseEntity<String>("There isn't cars", HttpStatus.NO_CONTENT);
                return response;
            }
            response = new ResponseEntity<List<CarDTO>>(carsDTO, HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get all cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllNotMarkAsDeleted(String[] sort) {
        ResponseEntity<?> response;
        try {
            List<Order> orders = CommonUtil.getInstance().getOrdersFromRequest(sort, Car.class);
            List<CarDTO> carsDTO = carRepository.findAllByDeleted(false, Sort.by(orders)).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            if (carsDTO.isEmpty()) {
                response = new ResponseEntity<String>("There isn't cars not mark as deleted", HttpStatus.NO_CONTENT);
                return response;
            }
            response = new ResponseEntity<List<CarDTO>>(carsDTO, HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get all not mark as deleted cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllFreeNotMarkAsDeleted(String[] sort) {
        ResponseEntity<?> response;
        try {
            List<Order> orders = CommonUtil.getInstance().getOrdersFromRequest(sort, Car.class);
            List<CarDTO> carsDTO = carRepository.findAllByEmploymentStatusAndDeleted(true, false, Sort.by(orders)).stream().map(car -> modelMapper.map(car, CarDTO.class)).collect(Collectors.toList());
            if (carsDTO.isEmpty()) {
                response = new ResponseEntity<String>("There isn't free cars not mark as deleted", HttpStatus.NO_CONTENT);
                return response;
            }
            response = new ResponseEntity<List<CarDTO>>(carsDTO, HttpStatus.OK);
        } catch (SortParametersNotValidException sortParametersNotValidException) {
            throw sortParametersNotValidException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to get all free and not mark as deleted cars", HttpStatus.INTERNAL_SERVER_ERROR);
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
            response = new ResponseEntity<CarDTO>(modelMapper.map(savedCar, CarDTO.class), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to create car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> update(Long id, CarDTO carDTO) {
        ResponseEntity<?> response;
        try {
            Car car = findCarByIdOrThrowException(id);
            CarUtil.getInstance().copyNotNullFieldsFromCarDTOToCar(carDTO, car);
            response = new ResponseEntity<CarDTO>(modelMapper.map(car, CarDTO.class), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to update car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        ResponseEntity<String> response;
        try {
            carRepository.delete(findCarByIdOrThrowException(id));
            response = new ResponseEntity<String>(new StringBuilder()
                    .append("Car with id = ")
                    .append(id)
                    .append(" was deleted from database").toString(), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to delete car", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ResponseEntity<String> markCarAsDeleted(Long id) {
        ResponseEntity<String> response;
        try {
            Car car = findCarByIdOrThrowException(id);
            if (car.isDeleted() == true) {
                response = new ResponseEntity<String>(new StringBuilder()
                        .append("Car with id = ")
                        .append(id)
                        .append(" already marked as deleted").toString(), HttpStatus.OK);
                return response;
            }
            car.setDeleted(true);
            response = new ResponseEntity<String>(new StringBuilder()
                    .append("Car with id = ")
                    .append(id)
                    .append(" was marked as deleted").toString(), HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            throw entityNotFoundException;
        } catch (Exception e) {
            response = new ResponseEntity<String>("Unable to mark car as deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    @Override
    public Car findCarByIdOrThrowException(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(new StringBuilder()
                .append("Car with id = ")
                .append(id)
                .append(" not found").toString()));
    }

}
