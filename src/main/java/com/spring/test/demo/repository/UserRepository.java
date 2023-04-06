package com.spring.test.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.test.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	@EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "users-graph")
	Optional<User> findByUsername(String username);
	
	List<User> findAll();
}
