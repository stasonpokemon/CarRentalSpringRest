package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
