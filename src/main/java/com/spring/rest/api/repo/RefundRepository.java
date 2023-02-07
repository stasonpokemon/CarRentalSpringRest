package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    Optional<Refund> findByOrder(Order order);
}
