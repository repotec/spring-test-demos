package com.spring.test;

import com.spring.test.model.Order;
import com.spring.test.repo.OrderRepository;
import com.spring.test.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
//    @Mock
//    private OrderRepository orderRepository;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    private Order order;
//
//    @BeforeAll
//    void setup(){
//        order = new Order();
//        order.setEmployeeId(1L);
//        order.setFirstName("ahmed");
//        order.setLastName("mohammed");
//    }
//
//    @JdbcTest
//    void givenStudentObject_whenSaveStudent_thenReturnStudentObject(){
//        BDDMockito.given(orderRepository.save(order)).willReturn(order);
//
//        // when - action or the behavior we are going to test
//        Order persistedOrder = orderService.saveEmployee(order);
//
//        // then - verify the output
//        Assertions.assertTrue(persistedOrder != null);
//    }
}
