package com.spring.test;

import com.spring.test.email.EmailService;
import com.spring.test.model.Customer;
import com.spring.test.model.Order;
import com.spring.test.model.OrderItem;
import com.spring.test.repo.OrderRepository;
import com.spring.test.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Stub_OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private EmailServiceStub emailServiceStub = new EmailServiceStub();

    /**
     * EmailServiceStub is a simple stub. It keeps track of whether sendOrderConfirmation was called. We inject this stub into
     * the OrderService before the test.
     */
    @Test
    void givenStudentObject_whenSaveStudent_thenReturnStudentObject(){
        Customer customer = new Customer(1L, "ahmed", "ahmed@gmail.com");
        OrderItem orderItem = new OrderItem(1L, "iphone");
        Order order = new Order();
        order.setOrderId(1L);
        order.setCustomer(customer);
        order.setTotal(100);
        order.setItems(Arrays.asList(orderItem));

        orderService.setEmailService(emailServiceStub);
        orderService.placeOrder(order);

        // Assert that the stub method was called
        Assertions.assertTrue(emailServiceStub.isEmailSent());
    }
}
