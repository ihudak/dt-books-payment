package com.dynatrace.payment.controller;

import com.dynatrace.payment.model.DynaPay;
import com.dynatrace.payment.model.Payment;
import com.dynatrace.payment.repository.ConfigRepository;
import com.dynatrace.payment.repository.DynaPayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController extends HardworkingController {
    @Autowired
    DynaPayRepository dynaPayRepository;
    @Autowired
    ConfigRepository configRepository;
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);



    // make a payment
    @PostMapping("")
    public Payment createPayment(@RequestBody Payment payment) {
        DynaPay dynaPay = dynaPayRepository.submitPayment(payment);
        payment.setSucceeded(dynaPay.isSucceeded());
        payment.setMessage(dynaPay.getMessage());
        logger.info("Payment Processing Info: " + dynaPay.isSucceeded() + " message: " + dynaPay.getMessage());
        return payment;
    }


    @Override
    public ConfigRepository getConfigRepository() {
        return configRepository;
    }
}
