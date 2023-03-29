package com.spring.rest.api.util;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import com.spring.rest.api.entity.mapper.CarMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarTestDataFactory {

    private static final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    public static Car buildCar() {
        return Car.builder()
                .model("testModel" + UUID.randomUUID())
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .deleted(false)
                .broken(false).build();
    }

    public static Car buildBrokenCar() {
        return Car.builder()
                .model("broken")
                .producer("testProducer2")
                .releaseDate(LocalDate.of(2001, 5, 4))
                .pricePerDay(200D)
                .employmentStatus(false)
                .damageStatus("With damage")
                .imageLink("")
                .deleted(false)
                .broken(true).build();
    }

    public static Car buildDeletedCar() {
        return Car.builder()
                .model("testModel")
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .employmentStatus(false)
                .damageStatus("Without damage")
                .imageLink("")
                .deleted(true)
                .broken(false).build();
    }

    public static CarResponseDTO buildCarResponseDTO(Car car) {
        return carMapper.carToCarResponseDTO(car);

    }

    public static CreateCarRequestDTO buildCreateCarRequestDTO(Car car) {
        return carMapper.carToCreateCarRequestDTO(car);
    }

    public static UpdateCarRequestDTO buildUpdateCarRequestDTO() {
        return UpdateCarRequestDTO.builder()
                .model("update")
                .producer("update")
                .releaseDate(LocalDate.of(2003, 11, 5))
                .pricePerDay(250D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("").build();
    }

    public static CarResponseDTO buildUpdatedCarResponseDTO() {
        return CarResponseDTO.builder()
                .model("update")
                .producer("update")
                .releaseDate(LocalDate.of(2003, 11, 5))
                .pricePerDay(250D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("").build();
    }

    public static CarResponseDTO buildFixedCarResponseDTO() {
        return CarResponseDTO.builder()
                .model("broken")
                .producer("testProducer2")
                .releaseDate(LocalDate.of(2001, 5, 4))
                .pricePerDay(200D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .broken(false).build();
    }

    public static CarResponseDTO buildDeletedFirstCarResponseDTO(Car car) {
        CarResponseDTO carResponseDTO = carMapper.carToCarResponseDTO(car);
        carResponseDTO.setEmploymentStatus(false);
        return carResponseDTO;
    }

}