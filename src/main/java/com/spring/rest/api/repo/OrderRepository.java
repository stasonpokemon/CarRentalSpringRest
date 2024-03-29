package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * JpaRepository, which works with Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
