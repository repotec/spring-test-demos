package com.spring.test.repo;


import com.spring.test.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findAll();
	Optional<Order> findByOrderId(@Param("orderId") Long orderId);
}
