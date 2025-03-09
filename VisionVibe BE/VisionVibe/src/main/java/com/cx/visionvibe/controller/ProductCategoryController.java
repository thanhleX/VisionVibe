package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.ProductCategoryCreationRequest;
import com.cx.visionvibe.dto.request.ProductCategoryUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.ProductCategoryResponse;
import com.cx.visionvibe.entity.ProductCategory;
import com.cx.visionvibe.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ApiResponse<List<ProductCategoryResponse>> findAllProductCategories() {
        return ApiResponse.<List<ProductCategoryResponse>>builder()
                .result(productCategoryService.findAllProductCategories())
                .build();
    }

    @PostMapping
    public ApiResponse<ProductCategoryResponse> createProductCategory(@RequestBody ProductCategoryCreationRequest request) {
        return ApiResponse.<ProductCategoryResponse>builder()
                .result(productCategoryService.createProductCategory(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductCategoryResponse> updateProductCategory(@PathVariable Long id, @RequestBody ProductCategoryUpdateRequest request) {
        return ApiResponse.<ProductCategoryResponse>builder()
                .result(productCategoryService.updateProductCategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
