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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 *
 * Spies are a hybrid between mocks and real objects. They wrap a real object and allow you to observe (spy on)
 * its interactions while potentially stubbing specific methods.
 *
 * - Leverages existing object behavior while allowing some method control.
 * - Useful for testing interactions with real objects but with controlled behavior for specific methods.
 * - Requires a real object to be created.
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class Spy_OrderServiceUnitTest {

    @Autowired
    private OrderRepository orderRepository;

    @Spy
    private EmailService emailService;

    @Autowired
    private OrderService orderService;

    private EmailServiceStub emailServiceStub = new EmailServiceStub();

    /**
     * emailService is a spy. We use doNothing and when to stub its sendOrderConfirmation to prevent actual emails during the test.
     * We can still verify that this method was called on the spy
     *
     * We here use a spy instead of Stub as we want to leverage the real object's behavior for most methods but need to control
     * specific interactions. Verification of those interactions is also important.
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

        Mockito.doNothing().when(emailService).sendOrderConfirmation(Mockito.isA(Order.class));

        orderService.placeOrder(order);

        // Verify interaction with the repository
        Mockito.verify(orderRepository).save(order);

        // Verify that emailService.sendOrderConfirmation() was called (but not its actual behavior)
        Mockito.verify(emailService, Mockito.atMostOnce()).sendOrderConfirmation(order);
    }
}
