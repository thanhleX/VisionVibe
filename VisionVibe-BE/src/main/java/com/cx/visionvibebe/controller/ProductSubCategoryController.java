package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewProductSubCategoryRequest;
import com.cx.visionvibebe.dto.request.UpdateSubCategoryRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.ProductSubCategoryResponse;
import com.cx.visionvibebe.service.ProductSubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("product-sub-categories")
@RequiredArgsConstructor
public class ProductSubCategoryController {
    private final ProductSubCategoryService productSubCategoryService;


    @GetMapping
    public ApiResponse<Page<ProductSubCategoryResponse>> findAllProductSubCategoriesByProductCategoryId(@RequestParam Long productCategoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1000") int size) {
        return ApiResponse.<Page<ProductSubCategoryResponse>>builder()
                .result(productSubCategoryService.findProductSubCategoriesByProductCategoryIdWithPagination(productCategoryId, page, size))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductSubCategoryResponse> findProductSubCategoryById(@PathVariable Long id) {
        return ApiResponse.<ProductSubCategoryResponse>builder()
                .result(productSubCategoryService.findProductSubCategoryById(id))
                .build();
    }

    @GetMapping("name")
    public ApiResponse<List<ProductSubCategoryResponse>> findProductSubCategoryByName(@RequestParam String categoryName) {
        return ApiResponse.<List<ProductSubCategoryResponse>>builder()
                .result(productSubCategoryService.findProductSubCategoryByProductCategoryName(categoryName))
                .build();
    }


    @PostMapping
    public ApiResponse<ProductSubCategoryResponse> addNewProductSubCategory(@ModelAttribute CreateNewProductSubCategoryRequest request) throws IOException {
        return ApiResponse.<ProductSubCategoryResponse>builder()
                .result(productSubCategoryService.addNewProductSubCategory(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductSubCategoryResponse> updateProductSubCategoryById(@ModelAttribute UpdateSubCategoryRequest request, @PathVariable Long id) throws IOException {
        return ApiResponse.<ProductSubCategoryResponse>builder()
                .result(productSubCategoryService.updateProductSubCategoryById(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteProductById(@PathVariable Long id) {
        productSubCategoryService.deleteProductSubCategoryById(id);
        return ApiResponse.builder().build();
    }
}
