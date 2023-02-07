package com.spring.rest.api.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/sql-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional(isolation = Isolation.READ_COMMITTED)
class CarServiceImplTest {




    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllNotMarkAsDeleted() {
    }

    @Test
    void findAllFreeNotMarkAsDeleted() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void fixBrokenCar() {
    }

    @Test
    void markCarAsDeleted() {
    }

    @Test
    void findCarByIdOrThrowException() {
    }
}