package com.spring.test;

import com.spring.test.model.Employee;
import com.spring.test.repo.EmployeeRepository;
import jakarta.annotation.Priority;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.time.LocalTime;


@DataJpaTest @Commit
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Execution(ExecutionMode.CONCURRENT)
@Slf4j
public class EmployeeRepositoryMySqlTest {
	@Autowired
	EmployeeRepository userRepository;

	@Test
	void test1() throws InterruptedException {
		log.info(Thread.currentThread().getName() + "|" + LocalTime.now());

		//given
		Employee employee = userRepository.findById(1L).orElseThrow();
		double expectedSalary = employee.getSalary() + 1000;
		employee.setSalary(expectedSalary);

		Thread.sleep(500);
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
