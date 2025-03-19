package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewProductCategoryRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.ProductCategoryResponse;
import com.cx.visionvibebe.dto.response.ProductCategoryResponseSimple;
import com.cx.visionvibebe.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ApiResponse<Page<ProductCategoryResponseSimple>> findAllProductCategoriesWithPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1000") int size) {
        return ApiResponse.<Page<ProductCategoryResponseSimple>>builder()
                .result(productCategoryService.findAllProductCategoriesWithPagination(page, size))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductCategoryResponseSimple> addNewProductCategory(@RequestBody CreateNewProductCategoryRequest request) {
        return ApiResponse.<ProductCategoryResponseSimple>builder()
                .result(productCategoryService.addNewProductCategory(request))
                .build();
    }

    @GetMapping("all")
    public ApiResponse<List<ProductCategoryResponse>> findAllProductCategories() {
        return ApiResponse.<List<ProductCategoryResponse>>builder()
                .result(productCategoryService.findAllProductCategories())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return ApiResponse.builder().build();
    }
}
