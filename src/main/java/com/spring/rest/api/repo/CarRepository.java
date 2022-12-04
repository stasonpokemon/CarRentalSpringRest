package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Car;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByDeleted(boolean isDeleted, Sort sort);

    List<Car> findAllByEmploymentStatusAndDeleted(boolean isFree, boolean isDeleted, Sort sort);
}
