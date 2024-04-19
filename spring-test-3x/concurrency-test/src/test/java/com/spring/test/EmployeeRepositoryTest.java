package com.spring.test;

import com.spring.test.model.Employee;
import com.spring.test.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalTime;


@DataJpaTest @Commit
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Execution(ExecutionMode.CONCURRENT)
@Slf4j
public class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository userRepository;

	@Test
	void test1(){
		log.info(Thread.currentThread().getName() + "|" + LocalTime.now());

		//given
		Employee employee = userRepository.findById(1L).orElseThrow();
		double expectedSalary = employee.getSalary() + 1000;
		employee.setSalary(expectedSalary);

		//when
		userRepository.save(employee);

		//then
		Employee actualEmployee = userRepository.findById(1L).orElseThrow();
		double actualSalary = actualEmployee.getSalary();
		Assertions.assertEquals(expectedSalary, actualSalary);
		log.info("actualSalary:" + actualSalary);
	}

	@Test
	void test2() {
		log.info(Thread.currentThread().getName() + "|" + LocalTime.now());

		//given
		Employee employee = userRepository.findById(1L).get();
		double expectedSalary = employee.getSalary() + 1000;
		employee.setSalary(expectedSalary);

		//when
		userRepository.save(employee);

		//then
		Employee actualEmployee = userRepository.findById(1L).get();
		double actualSalary = actualEmployee.getSalary();
		Assertions.assertEquals(expectedSalary, actualSalary);
		log.info("actualSalary:" + actualSalary);
	}

	@Test
	void test3() {
		log.info(Thread.currentThread().getName() + "|" + LocalTime.now());

		//given
		Employee employee = userRepository.findById(1L).get();
		double expectedSalary = employee.getSalary() + 1000;
		employee.setSalary(expectedSalary);

		//when
		userRepository.save(employee);

		//then
		Employee actualEmployee = userRepository.findById(1L).orElseThrow();
		double actualSalary = actualEmployee.getSalary();
		Assertions.assertEquals(expectedSalary, actualSalary);
		log.info("actualSalary:" + actualSalary);
	}
}
