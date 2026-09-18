package com.razorpay.payment_service.service;

import com.razorpay.payment_service.dto.request.CreateOrderRequest;
import com.razorpay.payment_service.dto.response.OrderResponse;
import com.razorpay.payment_service.dto.response.PaymentResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse create(UUID merchantId, @Valid CreateOrderRequest request);

    OrderResponse getById(UUID merchantId, UUID orderId);

    OrderResponse cancel(UUID merchantId, UUID orderId);

    List<PaymentResponse> listPayments(UUID merchantId, UUID orderId);
}
