package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByDeleted(boolean isDeleted, Pageable pageable);

    List<Car> findAllByEmploymentStatusAndDeleted(boolean isFree, boolean isDeleted, Pageable pageable);
}
