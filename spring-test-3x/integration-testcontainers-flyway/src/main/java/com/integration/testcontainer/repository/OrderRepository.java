package com.integration.testcontainer.repository;

import com.integration.testcontainer.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
