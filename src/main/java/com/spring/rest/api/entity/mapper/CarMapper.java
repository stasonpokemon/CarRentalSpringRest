package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import org.mapstruct.Mapper;

/**
 * This interface presents the basic contract for converting Car to CarDTO and vice versa.
 */
@Mapper
public interface CarMapper {

    CarResponseDTO carToCarResponseDTO(Car car);

    Car createCarRequestDTOToCar(CreateCarRequestDTO createCarRequestDTO);

    CreateCarRequestDTO carToCreateCarRequestDTO(Car car);

    Car carResponseDTOToCar(CarResponseDTO carResponseDTO);
}
