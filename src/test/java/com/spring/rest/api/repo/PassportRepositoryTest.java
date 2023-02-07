package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.User;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/sql-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional(isolation = Isolation.READ_COMMITTED)
class PassportRepositoryTest {

    private static final String STRING_OVER_500_SYMBOLS = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private UserRepository userRepository;

    private Passport passportForEachTest;

    private User userWithoutPassportForEachTest;

    @BeforeEach
    void createNewPassportBeforeEachTest() {
        passportForEachTest = new Passport();
        passportForEachTest.setName("SomeName");
        passportForEachTest.setSurname("SomeSurname");
        passportForEachTest.setPatronymic("SomePatronymic");
        passportForEachTest.setBirthday(new Date());
        passportForEachTest.setAddress("SomeAddress");

        userWithoutPassportForEachTest = userRepository.findById(4L).get();
    }

    @Test
    void findAllPassports() {
        assertEquals(3, passportRepository.findAll().size());
    }

    @Test
    void findPassportByUser() {
        Passport foundPassportByUser = passportRepository.findPassportByUser(userRepository.findById(1L).get()).get();
        Passport expectedPassport = userRepository.findById(1L).get().getPassport();
        assertEquals(expectedPassport, foundPassportByUser);
    }

    @Test
    void findPassportByUserWhoDoesntHavePassport() {
        assertFalse(passportRepository.findPassportByUser(userRepository.findById(4L).get()).isPresent());
    }

    @Test
    void saveNewPassportWithCorrectData() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        assertDoesNotThrow(() -> passportRepository.save(passportForEachTest));
        assertEquals(4, passportRepository.findAll().size());
        assertEquals(5, userRepository.findAll().size());
    }

    @Test
    void saveNewPassportWithUserWhoHasPassport() {
        User user = userRepository.findById(1L).get();
        passportForEachTest.setUser(user);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithWrongAddress() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setAddress(STRING_OVER_500_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithNullAddress() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setAddress(null);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithNullBirthday() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setBirthday(null);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithWrongName() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setName(STRING_OVER_500_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithNullName() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setName(null);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithWrongSurname() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setSurname(STRING_OVER_500_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithNullSurname() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setSurname(null);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithWrongPatronymic() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setPatronymic(STRING_OVER_500_SYMBOLS);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void saveNewPassportWithNullPatronymic() {
        passportForEachTest.setUser(userWithoutPassportForEachTest);
        passportForEachTest.setPatronymic(null);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.save(passportForEachTest));
    }

    @Test
    void updatePassportWithCorrectData() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setName("SomeName");
        passport.setSurname("SomeSurname");
        passport.setPatronymic("SomePatronymic");
        passport.setBirthday(new Date());
        passport.setAddress("SomeAddress");
        assertDoesNotThrow(() -> passportRepository.save(passport));
        assertEquals(3, passportRepository.findAll().size());
        assertEquals(5, userRepository.findAll().size());
    }

    @Test
    void updatePassportWithUserWhoHasPassport() {
        User user = userRepository.findById(1L).get();
        Passport passport = passportRepository.findById(2L).get();
        passport.setUser(user);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithWrongAddress() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setAddress(STRING_OVER_500_SYMBOLS);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithNullAddress() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setAddress(null);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithNullBirthday() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setBirthday(null);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithWrongName() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setName(STRING_OVER_500_SYMBOLS);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithNullName() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setName(null);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithWrongSurname() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setSurname(STRING_OVER_500_SYMBOLS);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithNullSurname() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setSurname(null);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithWrongPatronymic() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setPatronymic(STRING_OVER_500_SYMBOLS);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }

    @Test
    void updatePassportWithNullPatronymic() {
        Passport passport = passportRepository.findById(1L).get();
        passport.setPatronymic(null);
        passportRepository.save(passport);
        assertThrows(DataIntegrityViolationException.class, () -> passportRepository.findAll());
    }


}