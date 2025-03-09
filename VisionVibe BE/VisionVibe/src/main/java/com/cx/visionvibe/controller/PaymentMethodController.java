package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.PaymentMethodResponse;
import com.cx.visionvibe.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-method")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public ApiResponse<List<PaymentMethodResponse>> findAllPayments() {
        return ApiResponse.<List<PaymentMethodResponse>>builder()
                .result(paymentMethodService.findAllPayments())
                .build();
    }
}
