package com.razorpay.merchant_service.service;


import com.razorpay.merchant_service.dto.request.CreateApiKeyRequest;
import com.razorpay.merchant_service.dto.response.ApiKeyCreateResponse;
import jakarta.validation.Valid;

import java.util.List;
import com.razorpay.merchant_service.dto.response.ApiKeyResponse;
import java.util.UUID;

public interface ApiKeyService {
    ApiKeyCreateResponse create(UUID merchantId, @Valid CreateApiKeyRequest request);

    List<ApiKeyResponse> listByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId);
}
