package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.response.PaymentMethodResponse;
import com.cx.visionvibe.entity.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
}
