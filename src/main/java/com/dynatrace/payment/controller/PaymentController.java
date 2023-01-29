package com.dynatrace.payment.controller;

import com.dynatrace.payment.model.DynaPay;
import com.dynatrace.payment.model.Payment;
import com.dynatrace.payment.repository.DynaPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class PaymentController {
    @Value("${added.workload.cpu}")
    private long cpuPressure;
    @Value("${added.workload.ram}")
    private int memPressureMb;

    @Autowired
    DynaPayRepository dynaPayRepository;



    // make a payment
    @PostMapping("/payment")
    public Payment createPayment(@RequestBody Payment payment) {
        DynaPay dynaPay = dynaPayRepository.submitPayment(payment);
        payment.setSucceeded(dynaPay.isSucceeded());
        payment.setMessage(dynaPay.getMessage());
        return payment;
    }

    private void simulateHardWork() {
        int arraySize = (int)((long)this.memPressureMb * 1024L * 1024L / 8L);
        if (arraySize < 0) {
            arraySize = Integer.MAX_VALUE;
        }
        long[] longs = new long[arraySize];
        int j = 0;
        for(long i = 0; i < this.cpuPressure; i++, j++) {
            j++;
            if (j >= arraySize) {
                j = 0;
            }
            try {
                if (longs[j] > Integer.MAX_VALUE) {
                    longs[j] = (long) Integer.MIN_VALUE;
                }
            } catch (Exception ignored) {};
        }
    }
}
