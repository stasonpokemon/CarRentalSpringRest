package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.CarDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {

    CarDTO carToCarDTO(Car car);

    Car carDTOToCar(CarDTO carDTO);
}
