package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/sql-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional(isolation = Isolation.READ_COMMITTED)
@Disabled
class CarRepositoryTest {

    private static final String STRING_OVER_255_SYMBOLS = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Autowired
    private CarRepository carRepository;

    private Car carForEachTest;

    private UUID carId;

    @BeforeEach
    void createNewCarBeforeEachTest() {
        carForEachTest = new Car("test", "test", LocalDate.now(), 100D, true,
                "test", "test", false, false);
        carId = UUID.randomUUID();
    }

    @Test
    public void findCarExistById() {
        Optional<Car> carOptional = carRepository.findById(carId);
        assertTrue(carOptional.isPresent());
        carOptional.ifPresent(car -> assertEquals(carId, car.getId()));
    }

    @Test
    public void findCarByNotExistId() {
        assertFalse(carRepository.findById(carId).isPresent());
    }

    @Test
    public void findAllCars() {
        assertEquals(8, carRepository.findAll().size());
    }

    @Test
    public void saveNewCarWithCorrectData() {
        carRepository.save(carForEachTest);
        assertEquals(carForEachTest, carRepository.findById(carId).orElse(null));
    }

    @Test
    public void saveNewCarWithWrongProducer() {
        carForEachTest.setProducer(STRING_OVER_255_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void saveNewCarWithNullProducer() {
        carForEachTest.setProducer(null);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void saveNewCarWithWrongModel() {
        carForEachTest.setModel(STRING_OVER_255_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));

    }

    @Test
    public void saveNewCarWithNullModel() {
        carForEachTest.setModel(null);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void saveNewCarWithNullReleaseDate() {
        carForEachTest.setReleaseDate(null);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));

    }


    @Test
    public void saveNewCarWithWrongPricePerDay() {
        carForEachTest.setPricePerDay(-1D);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void saveNewCarWithNullPricePerDay() {
        carForEachTest.setPricePerDay(null);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void saveNewCarWithWrongDamageStatus() {
        carForEachTest.setDamageStatus(STRING_OVER_255_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void saveNewCarWithNullImageLink() {
        carForEachTest.setImageLink(null);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
    }

    @Test
    public void updateCarWithCorrectData() {
        Car car = carRepository.findById(carId).get();
        car.setModel("updated");
        carRepository.save(car);
        assertEquals(car.getModel(), carRepository.findById(carId).get().getModel());
    }

    @Test
    public void updateCarWithWrongProducer() {
        Car car = carRepository.findById(carId).get();
        car.setProducer(STRING_OVER_255_SYMBOLS);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }

    @Test
    public void updateCarWithNullProducer() {
        Car car = carRepository.findById(carId).get();
        car.setProducer(null);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }

    @Test
    public void updateCarWithWrongModel() {
        Car car = carRepository.findById(carId).get();
        car.setModel(STRING_OVER_255_SYMBOLS);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());

    }

    @Test
    public void updateCarWithNullModel() {
        Car car = carRepository.findById(carId).get();
        car.setModel(null);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }

    @Test
    public void updateCarWithNullReleaseDate() {
        Car car = carRepository.findById(carId).get();
        car.setReleaseDate(null);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }


    @Test
    public void updateCarWithWrongPricePerDay() {
        Car car = carRepository.findById(carId).get();
        car.setPricePerDay(-1D);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }

    @Test
    public void updateCarWithNullPricePerDay() {
        Car car = carRepository.findById(carId).get();
        car.setPricePerDay(null);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }

    @Test
    public void updateCarWithWrongDamageStatus() {
        Car car = carRepository.findById(carId).get();
        car.setDamageStatus(STRING_OVER_255_SYMBOLS);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
    }

    @Test
    public void updateCarWithNullImageLink() {
        Car car = carRepository.findById(carId).get();
        car.setImageLink(null);
        carRepository.save(car);
        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());

    }
}

