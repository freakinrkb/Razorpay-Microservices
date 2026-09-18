package com.razorpay.merchant_service.dto.request;


import com.razorpay.common_lib.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}
