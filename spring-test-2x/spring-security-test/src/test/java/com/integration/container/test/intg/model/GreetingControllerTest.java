package com.integration.container.test.intg.model;

import com.integration.container.test.service.GreetingService;
import com.integration.container.test.controller.GreetingController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


// A test class that loads only the web layer and tests a controller
@WebMvcTest(value = GreetingController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class GreetingControllerTest {

    // A mock object that simulates the service layer
    @MockBean
    private GreetingService greetingService;

    // A MockMvc instance that can perform mock HTTP requests
    @Autowired
    private MockMvc mockMvc;

    // A test method that verifies the GET /greeting endpoint
    @Test
    public void testGetGreeting() throws Exception {
        // Stub the greetingService to return a predefined greeting
        Mockito.when(greetingService.getGreeting()).thenReturn("Hello, world!");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/greeting")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // Perform a mock GET request to /greeting
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Hello, world!")));

        // Verify that the greetingService was called once
        Mockito.verify(greetingService, Mockito.times(1)).getGreeting();
    }
}