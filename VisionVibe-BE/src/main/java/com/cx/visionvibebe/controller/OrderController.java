package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewOrderRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.OrderResponse;
import com.cx.visionvibebe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ApiResponse<Page<OrderResponse>> findAllOrders(@RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .result(orderService.findAllOrders(page, size))
                .build();
    }

    @GetMapping("month")
    public ApiResponse<List<OrderResponse>> findAllOrdersInMonth() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.findAllOrdersInCurrentMonth())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> findOrderById(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.findOrderById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<OrderResponse> addNewOrder(@RequestBody CreateNewOrderRequest request) throws IOException {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.addNewOrder(request))
                .build();
    }

    @GetMapping("pending/{id}")
    public ApiResponse<OrderResponse> pendingOrder(@PathVariable Long id, @RequestParam String token) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.setPendingAfterPaidById(id, token))
                .build();
    }

    @GetMapping("confirm/{id}")
    public ApiResponse<OrderResponse> confirmOrder(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.confirmOrderById(id))
                .build();
    }

    @GetMapping("deny/{id}")
    public ApiResponse<OrderResponse> denyOrder(@PathVariable Long id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.denyOrderById(id))
                .build();
    }
}
