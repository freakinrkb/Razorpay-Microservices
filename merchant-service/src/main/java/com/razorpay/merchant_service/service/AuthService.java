package com.razorpay.merchant_service.service;

import com.razorpay.merchant_service.dto.request.LoginRequest;
import com.razorpay.merchant_service.dto.request.MerchantSignupRequest;
import com.razorpay.merchant_service.dto.response.LoginResponse;
import com.razorpay.merchant_service.dto.response.MerchantResponse;
import jakarta.validation.Valid;

public interface AuthService {
    MerchantResponse signup(@Valid MerchantSignupRequest request);

    LoginResponse login(@Valid LoginRequest request);
}
