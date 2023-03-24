//package com.spring.rest.api.repo;
//
//import com.spring.rest.api.entity.Role;
//import com.spring.rest.api.entity.User;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.InvalidDataAccessApiUsageException;
//import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@TestPropertySource("/application-test.properties")
//@Sql(value = {"/sql/sql-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Transactional(isolation = Isolation.READ_COMMITTED)
//@Log4j2
//class UserRepositoryTest {
//
//    private static final String STRING_OVER_255_SYMBOLS = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User userForEachTest;
//
//    @BeforeEach
//    void createNewUserBeforeEachTest() {
//        userForEachTest = new User();
//        userForEachTest.setUsername("test_username");
//        userForEachTest.setPassword("test_password");
//        userForEachTest.setEmail("test_email_123@gmail.com");
//        userForEachTest.setActive(false);
//        userForEachTest.setActivationCode("new_code");
//        userForEachTest.setRoles(Collections.singleton(Role.USER));
//    }
//
//    @Test
//    void findAllUsers() {
//        assertEquals(5, userRepository.findAll().size());
//    }
//
//    @Test
//    void findUserByExistId() {
//        assertTrue(userRepository.findById(1L).isPresent());
//    }
//
//    @Test
//    void findUserByNotExistId() {
//        assertFalse(userRepository.findById(10L).isPresent());
//    }
//
//    @Test
//    void findUserByNullId() {
//        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.findById(null).get());
//    }
//
//    @Test
//    void findUserByExistUsername() {
//        assertTrue(userRepository.findUserByUsername("admin").isPresent());
//    }
//
//    @Test
//    void findUserByNotExistUsername() {
//        assertFalse(userRepository.findUserByUsername("someName").isPresent());
//    }
//
//    @Test
//    void findUserByExistEmail() {
//        assertTrue(userRepository.findUserByEmail("stasonpokemon@icloud.com").isPresent());
//    }
//
//    @Test
//    void findUserByNotExistEmail() {
//        assertFalse(userRepository.findUserByEmail("some_email@icloud.com").isPresent());
//    }
//
//    @Test
//    void findUserByExistActivationCode() {
//        assertTrue(userRepository.findUserByActivationCode("test-code").isPresent());
//    }
//
//    @Test
//    void findUserByNotExistActivationCode() {
//        assertFalse(userRepository.findUserByActivationCode("some not exist code").isPresent());
//    }
//
//    @Test
//    void saveNewUserWithCorrectData() {
//        assertDoesNotThrow(() -> userRepository.save(userForEachTest));
//        assertEquals(userForEachTest, userRepository.findById(6L).get());
//    }
//
//    @Test
//    void saveNewUserWithAlreadyExistUsername() {
//        userForEachTest.setUsername("admin");
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//    @Test
//    void saveNewUserWithAlreadyExistEmail() {
//        userForEachTest.setEmail("stasonpokemon@icloud.com");
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//    @Test
//    void saveNewUserWithWrongUsername() {
//        userForEachTest.setUsername(STRING_OVER_255_SYMBOLS);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//    @Test
//    void saveNewUserWithNullUsername() {
//        userForEachTest.setUsername(null);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//
//    @Test
//    void saveNewUserWithWrongPassword() {
//        userForEachTest.setPassword(STRING_OVER_255_SYMBOLS);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//    @Test
//    void saveNewUserWithNullPassword() {
//        userForEachTest.setPassword(null);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//    @Test
//    void saveNewUserWithWrongEmail() {
//        userForEachTest.setEmail(STRING_OVER_255_SYMBOLS);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//    @Test
//    void saveNewUserWithNullEmail() {
//        userForEachTest.setEmail(null);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(userForEachTest));
//    }
//
//
//    @Test
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    void updateUserWithCorrectData() {
//        User user = userRepository.findById(1L).get();
//        user.setUsername("test_username");
//        user.setPassword("test_password");
//        user.setEmail("test_email_123@gmail.com");
//        user.setActive(false);
//        user.setActivationCode("new_code");
//        assertDoesNotThrow(() -> userRepository.save(user));
//        assertEquals(user, userRepository.findById(1L).get());
//    }
//
//    @Test
//    void updateUserWithAlreadyExistUsername() {
//        User user = userRepository.findById(2L).get();
//        user.setUsername("admin");
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//    }
//
//    @Test
//    void updateUserWithAlreadyExistEmail() {
//        User user = userRepository.findById(2L).get();
//        user.setEmail("stasonpokemon@icloud.com");
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//    }
//
//    @Test
//    void updateUserWithWrongUsername() {
//        User user = userRepository.findById(1L).get();
//        user.setUsername(STRING_OVER_255_SYMBOLS);
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//
//    }
//
//    @Test
//    void updateUserWithNullUsername() {
//        User user = userRepository.findById(1L).get();
//        user.setUsername(null);
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//    }
//
//
//    @Test
//    void updateUserWithWrongPassword() {
//        User user = userRepository.findById(1L).get();
//        user.setPassword(STRING_OVER_255_SYMBOLS);
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//    }
//
//    @Test
//    void updateUserWithNullPassword() {
//        User user = userRepository.findById(1L).get();
//        user.setPassword(null);
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//
//    }
//
//    @Test
//    void updateUserWithWrongEmail() {
//        User user = userRepository.findById(1L).get();
//        user.setEmail(STRING_OVER_255_SYMBOLS);
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//
//    }
//
//    @Test
//    void updateUserWithNullEmail() {
//        User user = userRepository.findById(1L).get();
//        user.setEmail(null);
//        userRepository.save(user);
//        assertThrows(DataIntegrityViolationException.class, () -> userRepository.findAll());
//
//    }
//
//
//}