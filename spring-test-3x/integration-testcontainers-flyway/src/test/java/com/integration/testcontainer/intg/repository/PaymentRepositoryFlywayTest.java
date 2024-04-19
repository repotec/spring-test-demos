package com.integration.testcontainer.intg.repository;

import com.integration.testcontainer.model.Order;
import com.integration.testcontainer.model.Payment;
import com.integration.testcontainer.repository.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // prevent database autoconfiguration that will be used by spring
@Testcontainers
@ActiveProfiles("test-fw")
public class PaymentRepositoryFlywayTest {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private TestEntityManager entityManager;


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
}
