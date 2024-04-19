package com.integration.container.test.intg.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.integration.container.test.model.Order;
import com.integration.container.test.model.Payment;
import com.integration.container.test.repository.PaymentRepository;
import jakarta.persistence.PersistenceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // prevent database autoconfiguration that will be used by spring
@Testcontainers
public class PaymentRepositoryTest {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@DynamicPropertySource
	static void configureTestProperties(DynamicPropertyRegistry registry){
		registry.add("spring.datasource.url",() -> mySQLContainer.getJdbcUrl());
		registry.add("spring.datasource.username",() -> mySQLContainer.getUsername());
		registry.add("spring.datasource.password",() -> mySQLContainer.getPassword());
		registry.add("spring.jpa.hibernate.ddl-auto",() -> "create-drop");
	}

	@Test
	void existingPaymentCanBeFound() {
		Order order = new Order(LocalDateTime.now(), 100.0, true);
		Payment payment = new Payment(order, "4532756279624064");

		Long orderId = entityManager.persist(order).getId();
		entityManager.persist(payment);

		Optional<Payment> savedPayment = paymentRepository.findByOrderId(orderId);

		Assertions.assertThat(savedPayment).isPresent();
		Assertions.assertThat(savedPayment.get().getOrder().getPaid()).isTrue();
	}

	@Test
	void paymentsAreUniquePerOrder() {
		Order order = new Order(LocalDateTime.now(), 100.0, true);
		Payment first = new Payment(order, "4532756279624064");
		Payment second = new Payment(order, "4716327217780406");

		entityManager.persist(order);
		entityManager.persist(first);

		org.junit.jupiter.api.Assertions.assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(second));
	}

	@Test
	@Sql("/multiple-payments.sql")
	void findPaymentsAfterDate() {
		List<Payment> payments = paymentRepository.findAllAfter(LocalDateTime.now().minusDays(1));

		Assertions.assertThat(payments).extracting("order.id").containsOnly(1L);
	}

	@Test
	@Sql("/multiple-payments.sql")
	void findPaymentsByCreditCard() {
		List<Payment> payments = paymentRepository.findByCreditCardNumber("4532756279624064");

		Assertions.assertThat(payments).extracting("order.id").containsOnly(1L);
	}

}
