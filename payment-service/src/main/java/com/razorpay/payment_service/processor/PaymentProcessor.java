package com.razorpay.payment_service.processor;

import com.razorpay.payment_service.processor.dto.PaymentProcessorRequest;
import com.razorpay.payment_service.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    PaymentProcessorResponse charge(PaymentProcessorRequest request);

}

