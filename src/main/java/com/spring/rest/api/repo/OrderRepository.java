package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUserId(Long id);
    List<Order> findOrderByOrderStatus(OrderStatus orderStatus);
}
