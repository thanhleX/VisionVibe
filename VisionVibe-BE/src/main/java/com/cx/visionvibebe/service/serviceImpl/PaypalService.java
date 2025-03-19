package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaypalService {
    private final PayPalHttpClient payPalHttpClient;

    public String createOrder(Double totalAmount, Long orderId) throws IOException {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Hyperion")
                .landingPage("BILLING")
                .returnUrl("http://localhost:4200/payment/" + orderId);
        orderRequest.applicationContext(applicationContext);

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode("USD")
                        .value(String.format("%.2f", totalAmount)));

        orderRequest.purchaseUnits(Collections.singletonList(purchaseUnitRequest));

        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);

        HttpResponse<Order> response = payPalHttpClient.execute(request);

        Order order = response.result();

        return order.links().stream()
                .filter(link -> link.rel().equals("approve"))
                .findFirst()
                .orElseThrow(() -> new IOException("Approval link not found"))
                .href();
    }

    public boolean checkPaymentStatus(String token) {
        OrdersGetRequest request = new OrdersGetRequest(token);
        try {
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            Order order = response.result();
            log.warn(order.status());

            if ("APPROVED".equals(order.status())) return true;

        } catch (IOException e) {
            throw new AppException(ErrorCode.PAYPAL_TOKEN_NOT_VALID);
        }
        throw new AppException(ErrorCode.PAYPAL_TOKEN_DENIED);
    }
}
