//package com.spring.rest.api.repo;
//
//import com.spring.rest.api.entity.Car;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@TestPropertySource("/application-test.properties")
//@Sql(value = {"/sql/sql-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Transactional(isolation = Isolation.READ_COMMITTED)
//class CarRepositoryTest {
//
//    private static final String STRING_OVER_255_SYMBOLS = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//
//    @Autowired
//    private CarRepository carRepository;
//
//    private Car carForEachTest;
//
//    @BeforeEach
//    void createNewCarBeforeEachTest() {
//        carForEachTest = new Car("test", "test", LocalDate.now(), 100D, true, "test", "test", false, false);
//
//    }
//
//    @Test
//    public void findCarExistById() {
//        Optional<Car> carOptional = carRepository.findById(1L);
//        assertTrue(carOptional.isPresent());
//        carOptional.ifPresent(car -> assertEquals(1, car.getId()));
//    }
//
//    @Test
//    public void findCarByNotExistId() {
//        assertFalse(carRepository.findById(9L).isPresent());
//    }
//
//    @Test
//    public void findAllCars() {
//        assertEquals(8, carRepository.findAll().size());
//    }
//
//    @Test
//    public void saveNewCarWithCorrectData() {
//        carRepository.save(carForEachTest);
//        assertEquals(carForEachTest, carRepository.findById(9L).orElse(null));
//    }
//
//    @Test
//    public void saveNewCarWithWrongProducer() {
//        carForEachTest.setProducer(STRING_OVER_255_SYMBOLS);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void saveNewCarWithNullProducer() {
//        carForEachTest.setProducer(null);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void saveNewCarWithWrongModel() {
//        carForEachTest.setModel(STRING_OVER_255_SYMBOLS);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//
//    }
//
//    @Test
//    public void saveNewCarWithNullModel() {
//        carForEachTest.setModel(null);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void saveNewCarWithNullReleaseDate() {
//        carForEachTest.setReleaseDate(null);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//
//    }
//
//
//    @Test
//    public void saveNewCarWithWrongPricePerDay() {
//        carForEachTest.setPricePerDay(-1D);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void saveNewCarWithNullPricePerDay() {
//        carForEachTest.setPricePerDay(null);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void saveNewCarWithWrongDamageStatus() {
//        carForEachTest.setDamageStatus(STRING_OVER_255_SYMBOLS);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void saveNewCarWithNullImageLink() {
//        carForEachTest.setImageLink(null);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.save(carForEachTest));
//    }
//
//    @Test
//    public void updateCarWithCorrectData() {
//        Car car = carRepository.findById(1L).get();
//        car.setModel("updated");
//        carRepository.save(car);
//        assertEquals(car.getModel(), carRepository.findById(1L).get().getModel());
//    }
//
//    @Test
//    public void updateCarWithWrongProducer() {
//        Car car = carRepository.findById(1L).get();
//        car.setProducer(STRING_OVER_255_SYMBOLS);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//    @Test
//    public void updateCarWithNullProducer() {
//        Car car = carRepository.findById(1L).get();
//        car.setProducer(null);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//    @Test
//    public void updateCarWithWrongModel() {
//        Car car = carRepository.findById(1L).get();
//        car.setModel(STRING_OVER_255_SYMBOLS);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//
//    }
//
//    @Test
//    public void updateCarWithNullModel() {
//        Car car = carRepository.findById(1L).get();
//        car.setModel(null);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//    @Test
//    public void updateCarWithNullReleaseDate() {
//        Car car = carRepository.findById(1L).get();
//        car.setReleaseDate(null);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//
//    @Test
//    public void updateCarWithWrongPricePerDay() {
//        Car car = carRepository.findById(1L).get();
//        car.setPricePerDay(-1D);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//    @Test
//    public void updateCarWithNullPricePerDay() {
//        Car car = carRepository.findById(1L).get();
//        car.setPricePerDay(null);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//    @Test
//    public void updateCarWithWrongDamageStatus() {
//        Car car = carRepository.findById(1L).get();
//        car.setDamageStatus(STRING_OVER_255_SYMBOLS);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//    }
//
//    @Test
//    public void updateCarWithNullImageLink() {
//        Car car = carRepository.findById(1L).get();
//        car.setImageLink(null);
//        carRepository.save(car);
//        assertThrows(DataIntegrityViolationException.class, () -> carRepository.findAll());
//
//    }
//
//}