package com.spring.rest.api.service;

import com.spring.rest.api.entity.Car;
import com.spring.rest.api.entity.dto.request.CreateCarRequestDTO;
import com.spring.rest.api.entity.dto.request.UpdateCarRequestDTO;
import com.spring.rest.api.entity.dto.response.CarResponseDTO;
import com.spring.rest.api.exception.NotFoundException;
import com.spring.rest.api.repo.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    private Car firstCar;

    private Car secoundCar;

    private Car brokenCar;

    private Car deletedCar;

    private CarResponseDTO firstCarResponseDTO;

    private CreateCarRequestDTO firstCreateCarRequestDTo;

    private UpdateCarRequestDTO updateCarRequestDTO;

    private CarResponseDTO updatedCarResponseDTO;

    private CarResponseDTO fixedCarResponseDTO;

    private CarResponseDTO deletedFirstCarResponseDTO;

    private Page<Car> carsPage;

    private Page<CarResponseDTO> carDTOsPage;

    private Pageable pageRequest;


    @BeforeEach
    void init() {
        firstCar = Car.builder()
                .model("testModel")
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .deleted(false)
                .broken(false).build();

        secoundCar = Car.builder()
                .model("testModel2")
                .producer("testProducer2")
                .releaseDate(LocalDate.of(2001, 5, 4))
                .pricePerDay(200D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .deleted(false)
                .broken(false).build();

        brokenCar = Car.builder()
                .model("broken")
                .producer("testProducer2")
                .releaseDate(LocalDate.of(2001, 5, 4))
                .pricePerDay(200D)
                .employmentStatus(false)
                .damageStatus("With damage")
                .imageLink("")
                .deleted(false)
                .broken(true).build();

        deletedCar = Car.builder()
                .model("testModel")
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .employmentStatus(false)
                .damageStatus("Without damage")
                .imageLink("")
                .deleted(true)
                .broken(false).build();

        firstCarResponseDTO = CarResponseDTO.builder()
                .model("testModel")
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .broken(false).build();

        CarResponseDTO secondCarResponseDTO = CarResponseDTO.builder()
                .model("testModel2")
                .producer("testProducer2")
                .releaseDate(LocalDate.of(2001, 5, 4))
                .pricePerDay(200D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .broken(false).build();

        firstCreateCarRequestDTo = CreateCarRequestDTO.builder()
                .model("testModel")
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .imageLink("").build();

        updateCarRequestDTO = UpdateCarRequestDTO.builder()
                .model("update")
                .producer("update")
                .releaseDate(LocalDate.of(2003, 11, 5))
                .pricePerDay(250D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("").build();

        updatedCarResponseDTO = CarResponseDTO.builder()
                .model("update")
                .producer("update")
                .releaseDate(LocalDate.of(2003, 11, 5))
                .pricePerDay(250D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("").build();

        fixedCarResponseDTO = CarResponseDTO.builder()
                .model("broken")
                .producer("testProducer2")
                .releaseDate(LocalDate.of(2001, 5, 4))
                .pricePerDay(200D)
                .employmentStatus(true)
                .damageStatus("Without damage")
                .imageLink("")
                .broken(false).build();

        deletedFirstCarResponseDTO = CarResponseDTO.builder()
                .model("testModel")
                .producer("testProducer")
                .releaseDate(LocalDate.of(2001, 7, 13))
                .pricePerDay(100D)
                .employmentStatus(false)
                .damageStatus("Without damage")
                .imageLink("")
                .broken(false).build();

        carsPage = new PageImpl<>(List.of(firstCar, secoundCar));

        carDTOsPage = new PageImpl<>(List.of(firstCarResponseDTO, secondCarResponseDTO));

        pageRequest = PageRequest.of(0, 2);

    }

    @Test
    void findById_WhenThereIsCarWithSpecifiedId_ReturnCar() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.of(firstCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.findById(1L);
        CarResponseDTO responseBody = (CarResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstCarResponseDTO, responseBody);

        verify(carRepository).findById(1L);
    }

    @Test
    void findById_WhenThereIsNotCarWithSpecifiedId_ThrowsNotFoundException() {
        //given - precondition or setup
        when(carRepository.findById(11L)).thenReturn(Optional.empty());

        //then - verify the output
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> carService.findById(11L));
        assertEquals("Not found Car with id: 11", notFoundException.getMessage());

        verify(carRepository).findById(11L);
    }

    @Test
    void findAll_ReturnPageOfCars() {
        //given - precondition or setup
        when(carRepository.findAll(pageRequest)).thenReturn(carsPage);

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.findAll(pageRequest);
        Page<CarResponseDTO> responseBody = (Page<CarResponseDTO>) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carDTOsPage, responseBody);

        verify(carRepository).findAll(pageRequest);
    }

    @Test
    void findAllNotMarkAsDeleted_WhenThereIsCarsNotMarkAsDeleted_ReturnPageOfCars() {
        //given - precondition or setup
        when(carRepository.findAllByDeleted(false, pageRequest)).thenReturn(List.of(firstCar, secoundCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.findAllNotMarkAsDeleted(pageRequest);
        Page<CarResponseDTO> responseBody = (Page<CarResponseDTO>) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carDTOsPage, responseBody);

        verify(carRepository).findAllByDeleted(false, pageRequest);
    }

    @Test
    void findAllNotMarkAsDeleted_WhenThereIsNotCarsNotMarkAsDeleted_ThrowsNotNullException() {
        //given - precondition or setup
        when(carRepository.findAllByDeleted(false, pageRequest)).thenReturn(Collections.emptyList());

        //then - verify the output
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> carService.findAllNotMarkAsDeleted(pageRequest));
        assertEquals("There isn't cars not mark as deleted", notFoundException.getMessage());

        verify(carRepository).findAllByDeleted(false, pageRequest);
    }

    @Test
    void findAllFreeNotMarkAsDeleted() {
    }

    @Test
    void save_WhenCarValid_ReturnCreatedCar() {
        //given - precondition or setup
        when(carRepository.save(firstCar)).thenReturn(firstCar);

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.save(firstCreateCarRequestDTo);
        CarResponseDTO responseBody = (CarResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstCarResponseDTO, responseBody);

        verify(carRepository).save(firstCar);
    }

    @Test
    void update_WhenCarWithSpecifiedIdExists_ReturnUpdatedCar() {
        //given - precondition or setup
        when(carRepository.findById(2L)).thenReturn(Optional.of(secoundCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.update(2L, updateCarRequestDTO);
        Object responseBody = response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCarResponseDTO, responseBody);

        verify(carRepository).findById(2L);
    }

    @Test
    void update_WhenCarWithSpecifiedIdNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        when(carRepository.findById(3L)).thenReturn(Optional.empty());

        //then - verify the output
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> carService.update(3L, updateCarRequestDTO));
        assertEquals("Not found Car with id: 3", notFoundException.getMessage());
    }

    @Test
    void fixBrokenCar_WhenCarBroken_ReturnFixedCar() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.of(brokenCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.fixBrokenCar(1L);
        CarResponseDTO responseBody = (CarResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fixedCarResponseDTO, responseBody);

        verify(carRepository).findById(1L);
    }

    @Test
    void fixBrokenCar_WhenCarWithSpecifiedIdNotExists_ThrowsNotFoundException() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        //then - verify the output
        NotFoundException notFoundException
                = assertThrows(NotFoundException.class, () -> carService.fixBrokenCar(1L));
        assertEquals("Not found Car with id: 1", notFoundException.getMessage());

        verify(carRepository).findById(1L);
    }

    @Test
    void fixBrokenCar_WhenCarDoesNotBroken_ReturnStringMessage() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.of(firstCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.fixBrokenCar(1L);
        String responseBody = (String) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Unable to fix car. Car with id = 1 already fixed", responseBody);

        verify(carRepository).findById(1L);
    }

    @Test
    void fixBrokenCar_WhenCarMarkedAsDeleted_ReturnStringMessage() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.of(deletedCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.fixBrokenCar(1L);
        String responseBody = (String) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Unable to fix car. Car with id = 1 is deleted", responseBody);

        verify(carRepository).findById(1L);
    }

    @Test
    void markCarAsDeleted_WhenCarDoseNotMarkedAsDeleted_ReturnMarkedAsDeletedCar() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.of(firstCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.markCarAsDeleted(1L);
        CarResponseDTO responseBody = (CarResponseDTO) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deletedFirstCarResponseDTO, responseBody);

        verify(carRepository).findById(1L);
    }

    @Test
    void markCarAsDeleted_WhenCarAlreadyMarkedAsDeleted_ReturnStringMessage() {
        //given - precondition or setup
        when(carRepository.findById(1L)).thenReturn(Optional.of(deletedCar));

        //when - action or the behaviour that we are going test
        ResponseEntity<?> response = carService.markCarAsDeleted(1L);
        String responseBody = (String) response.getBody();

        //then - verify the output
        assertNotNull(response);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Car with id = 1 already marked as deleted", responseBody);

        verify(carRepository).findById(1L);
    }
}