package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodResponse> findAllPaymentMethod();
}
