package com.razorpay.merchant_service.mapper;

import com.razorpay.merchant_service.dto.response.ApiKeyCreateResponse;
import com.razorpay.merchant_service.dto.response.ApiKeyResponse;
import com.razorpay.merchant_service.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {

    @Mapping(target = "keySecret", expression = "java(rawSecret)")
    ApiKeyCreateResponse toCreateResponse(ApiKey apiKey, String rawSecret);

    List<ApiKeyResponse> toResponseList(List<ApiKey> apiKeyList);
}
