package com.spring.test.service;


import com.spring.test.email.EmailService;
import com.spring.test.email.EmailServiceImpl;
import com.spring.test.model.Order;
import com.spring.test.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository employeeRepository;

	private EmailService emailService;


	public List<Order> getAllOrders() {
		return employeeRepository.findAll();
	}

	public Optional<Order> getOrderById(Long employeeId) {
		return employeeRepository.findByOrderId(employeeId);
	}
	
	public Order placeOrder(Order order) {
		Order persistedOrder = employeeRepository.save(order);
		emailService.sendOrderConfirmation(order);
		return persistedOrder;
	}

	public void setEmailService(EmailService emailService){
		this.emailService = emailService;
	}
}
