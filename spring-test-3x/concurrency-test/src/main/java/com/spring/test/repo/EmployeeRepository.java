package com.spring.test.repo;

import com.spring.test.model.Employee;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	List<Employee> findAll();

	@Query("select e from Employee e where e.employeeId = :id")
	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	//@Transactional(isolation = Isolation.SERIALIZABLE)
	Optional<Employee> findById(@Param("id") Long id);
}
