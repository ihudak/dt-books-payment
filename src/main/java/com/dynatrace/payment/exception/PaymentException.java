package com.dynatrace.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PaymentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PaymentException(String message) {
        super(message);
    }
}
