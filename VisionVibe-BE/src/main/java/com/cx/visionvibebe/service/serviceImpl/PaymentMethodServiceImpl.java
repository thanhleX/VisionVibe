package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.response.PaymentMethodResponse;
import com.cx.visionvibebe.mapper.PaymentMethodMapper;
import com.cx.visionvibebe.repository.PaymentMethodRepository;
import com.cx.visionvibebe.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Override
    public List<PaymentMethodResponse> findAllPaymentMethod() {
        return paymentMethodRepository.findAll().stream().map(paymentMethodMapper::toPaymentMethodResponse).toList();
    }
}
