package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.response.PaymentMethodResponse;
import com.cx.visionvibebe.entity.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod);
}
