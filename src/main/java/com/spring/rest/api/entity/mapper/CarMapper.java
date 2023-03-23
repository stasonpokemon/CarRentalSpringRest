package com.spring.rest.api.entity.mapper;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import com.spring.rest.api.entity.dto.request.CreatOrUpdateCarRequestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {

    CarResponseDTO carToCarDTO(Car car);

    Car createCarRequestDTOToCar(CreatOrUpdateCarRequestDTO creatOrUpdateCarRequestDTO);
}
