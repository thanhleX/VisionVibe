package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.ProductCreationRequest;
import com.cx.visionvibe.dto.request.ProductUpdateRequest;
import com.cx.visionvibe.dto.request.PromotionCreationRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.ProductResponse;
import com.cx.visionvibe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> findAllProductByCategoryName(@RequestParam String categoryName, @RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.findAllProductByProductCategoryName(categoryName, page, size))
                .build();
    }

    @GetMapping("/name")
    public ApiResponse<ProductResponse> findProductByName(@RequestParam String productName) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findProductByProductName(productName))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> findProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findProductById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreationRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
