package com.spring.test.controller;

import com.spring.test.dto.EmailDto;
import com.spring.test.model.Order;
import com.spring.test.email.EmailServiceImpl;
import com.spring.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long orderId) {
        return orderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> placeNewOrder(@RequestBody Order order) {
        EmailServiceImpl emailServiceImpl =  EmailServiceImpl.builder().host("smtp.gmail.com")
                                                                       .senderEmail("system@info.com")
                                                                       .senderPassword("0102898462")
                                                                       .build();

		emailServiceImpl.sendOrderConfirmation(order);

        return new ResponseEntity<Order> (orderService.placeOrder(order), HttpStatus.CREATED);
    }
}