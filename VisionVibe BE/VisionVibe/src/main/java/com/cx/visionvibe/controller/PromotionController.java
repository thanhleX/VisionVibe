package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.PromotionCreationRequest;
import com.cx.visionvibe.dto.request.PromotionUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.PromotionResponse;
import com.cx.visionvibe.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public ApiResponse<List<PromotionResponse>> getAllPromotions() {
        return ApiResponse.<List<PromotionResponse>>builder()
                .result(promotionService.findAllPromotions())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PromotionResponse> getPromotionById(@PathVariable Long id) {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.findPromotionById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<PromotionResponse> createPromotion(@RequestBody PromotionCreationRequest request) throws IOException {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.createPromotion(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PromotionResponse> updatePromotion(@PathVariable Long id, @RequestBody PromotionUpdateRequest request) throws IOException {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.updatePromotion(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePromotion(@PathVariable Long id) throws IOException {
        promotionService.deletePromotion(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
