package com.integration.container.test.intg.controller;

import javax.persistence.EntityNotFoundException;

import com.integration.container.test.dto.OrderAlreadyPaid;
import com.integration.container.test.controller.OrderController;
import com.integration.container.test.model.Order;
import com.integration.container.test.model.Payment;
import com.integration.container.test.model.Receipt;
import com.integration.container.test.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = OrderController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class)
class OrderControllerTests {
    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    /**
     *
     * @throws Exception
     */
    @Test
    void payOrder() throws Exception {
        Order order = new Order(1L, LocalDateTime.now(), 100.0, false);
        Payment payment = new Payment(1000L, order, "4532756279624064");

        Mockito.when(orderService.pay(ArgumentMatchers.eq(1L), ArgumentMatchers.eq("4532756279624064"))).thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders.post("/order/{id}/payment", 1L)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content("{\"creditCardNumber\": \"4532756279624064\"}"))
                                            .andExpect(MockMvcResultMatchers.status().isCreated())
                                            .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "http://localhost/order/1/receipt"));
    }

    @Test
    void paymentFailsWhenOrderIsNotFound() throws Exception {
        Mockito.when(orderService.pay(ArgumentMatchers.eq(1L), any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/order/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"creditCardNumber\": \"4532756279624064\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void paymentFailsWhenCreditCardNumberNotGiven() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/order/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void cannotPayAlreadyPaidOrder() throws Exception {
        Mockito.when(orderService.pay(ArgumentMatchers.eq(1L), any())).thenThrow(OrderAlreadyPaid.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/order/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"creditCardNumber\": \"4532756279624064\"}"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void receiptCanBeFound() throws Exception {
        Receipt receipt = new Receipt(LocalDateTime.now(), "4532756279624064", 100.0);

        Mockito.when(orderService.getReceipt(ArgumentMatchers.eq(1L))).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}/receipt", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * serialization Receipt object test
     * @throws Exception
     */
    @Test
    void getReceiptForOrder() throws Exception {
        Receipt receipt = new Receipt(
                LocalDateTime.now(),
                "4532756279624064",
                100.0);

        Mockito.when(orderService.getReceipt(ArgumentMatchers.eq(1L))).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}/receipt", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCardNumber").value("4532756279624064"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0));
    }
}
