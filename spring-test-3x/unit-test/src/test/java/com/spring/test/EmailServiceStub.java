package com.spring.test;

import com.spring.test.email.EmailService;
import com.spring.test.model.Order;

public class EmailServiceStub implements EmailService {
    @Override
    public void sendOrderConfirmation(Order order) {
        //nothing todo
    }

    @Override
    public boolean isEmailSent() {
        return true; //force true
    }
}
