package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.response.PaymentMethodResponse;
import com.cx.visionvibe.mapper.PaymentMethodMapper;
import com.cx.visionvibe.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public List<PaymentMethodResponse> findAllPayments() {
        return paymentMethodRepository.findAll().stream()
                .map(paymentMethodMapper::toPaymentMethodResponse).toList();
    }
}
