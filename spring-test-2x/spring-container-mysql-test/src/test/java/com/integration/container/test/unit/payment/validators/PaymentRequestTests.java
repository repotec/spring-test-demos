package com.integration.container.test.unit.payment.validators;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.integration.container.test.dto.PaymentRequest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentRequestTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void creditCardNumberMustNotBeNull() {
        PaymentRequest request = new PaymentRequest(null);

        //to get all validators in PaymentRequest object, which is org.hibernate.validator.constraints.CreditCardNumber annotation
        Set<ConstraintViolation<PaymentRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void creditCardNumberMustBeValid() {
        String creditCardNumberWithInvalidChecksum = "4532756279624063";
        PaymentRequest request = new PaymentRequest(creditCardNumberWithInvalidChecksum);

        Set<ConstraintViolation<PaymentRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }
}
