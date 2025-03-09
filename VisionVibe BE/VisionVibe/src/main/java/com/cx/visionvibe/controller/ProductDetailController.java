package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.ProductDetailCreationRequest;
import com.cx.visionvibe.dto.request.ProductDetailUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.ProductDetailResponse;
import com.cx.visionvibe.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product-details")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping
    public ApiResponse<List<ProductDetailResponse>> findAllProductDetailByProductName(@RequestParam String productName) {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .result(productDetailService.findAllProductDetailByProductName(productName))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDetailResponse> findProductDetailById(@PathVariable Long id) {
        return ApiResponse.<ProductDetailResponse>builder()
                .result(productDetailService.findProductDetailById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductDetailResponse> createProductDetail(@RequestBody ProductDetailCreationRequest request) throws IOException {
        return ApiResponse.<ProductDetailResponse>builder()
                .result(productDetailService.createProductDetailByProductName(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductDetailResponse> updateProductDetail(@PathVariable Long id, @RequestBody ProductDetailUpdateRequest request) throws IOException {
        return ApiResponse.<ProductDetailResponse>builder()
                .result(productDetailService.updateProductDetailById(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductDetail(@PathVariable Long id) {
        productDetailService.deleteProductDetailById(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
