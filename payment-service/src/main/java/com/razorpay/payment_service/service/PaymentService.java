package com.razorpay.payment_service.service;

import com.razorpay.payment_service.dto.request.PaymentInitRequest;
import com.razorpay.payment_service.dto.response.PaymentResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse initiate(UUID merchantId, @Valid PaymentInitRequest request, String idempotencyKey);

    PaymentResponse capture(UUID merchantId, UUID paymentId);

    void resolveAuthorization(UUID paymentId, boolean approve, String bankRef, String errorCode, String errorDescription);

}
