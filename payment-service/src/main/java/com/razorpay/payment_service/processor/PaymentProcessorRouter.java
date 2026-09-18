package com.razorpay.payment_service.processor;

import com.razorpay.common_lib.enums.PaymentMethod;
import com.razorpay.payment_service.processor.dto.PaymentProcessorRequest;
import com.razorpay.payment_service.processor.dto.PaymentProcessorResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentProcessorRouter {

    private final Map<PaymentMethod, PaymentProcessor> paymentProcessors;

    public PaymentProcessorRouter(Map<PaymentMethod, PaymentProcessor> paymentProcessors) {
        this.paymentProcessors = paymentProcessors;
    }

    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        PaymentProcessor processor = paymentProcessors.get(request.method());
        if (processor == null) {
            throw new IllegalArgumentException("No payment processor registered for method: "+request.method());
        }
        return processor.charge(request);
    }
}