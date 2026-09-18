package com.razorpay.payment_service.mapper;

import com.razorpay.payment_service.dto.response.OrderResponse;
import com.razorpay.payment_service.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(source = "orderStatus", target = "status")
    OrderResponse toResponse(OrderRecord orderRecord);
}
