package com.spring.test.email;

import com.spring.test.model.Order;

public interface EmailService {
    void sendOrderConfirmation(Order order);
    boolean isEmailSent();
}
