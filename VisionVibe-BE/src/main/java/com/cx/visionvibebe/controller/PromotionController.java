package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewPromotionRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.PromotionResponse;
import com.cx.visionvibebe.dto.response.UpdatePromotionRequest;
import com.cx.visionvibebe.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("promotion")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public ApiResponse<List<PromotionResponse>> findAllPromotions() {
        return ApiResponse.<List<PromotionResponse>>builder()
                .result(promotionService.findAllPromotions())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PromotionResponse> findPromotionById(@PathVariable Long id) {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.findPromotionById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<PromotionResponse> createPromotion(@RequestBody CreateNewPromotionRequest request) throws IOException {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.addNewPromotion(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PromotionResponse> updatePromotion(@PathVariable Long id, @RequestBody UpdatePromotionRequest request) throws IOException {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.updatePromotion(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotionById(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
