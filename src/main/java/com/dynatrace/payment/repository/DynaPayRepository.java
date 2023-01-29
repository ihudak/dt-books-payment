package com.dynatrace.payment.repository;

import com.dynatrace.payment.exception.PaymentException;
import com.dynatrace.payment.model.DynaPay;
import com.dynatrace.payment.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class DynaPayRepository {
    @Value("${http.service.dynapay}")
    private String dynaPayBaseURL;
    private RestTemplate restTemplate;

    public DynaPayRepository() {
        restTemplate = new RestTemplate();
    }



    public DynaPay submitPayment(@NonNull Payment payment) {
        String urlBuilder = dynaPayBaseURL;
        DynaPay dynaPay = restTemplate.postForObject(urlBuilder, payment, DynaPay.class);
        if (dynaPay == null || !dynaPay.isSucceeded()) {
            throw new PaymentException("Purchase was rejected: " + dynaPay.getMessage());
        }
        return dynaPay;
    }
}
