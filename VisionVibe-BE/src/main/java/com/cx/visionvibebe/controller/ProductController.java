package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewProductRequest;
import com.cx.visionvibebe.dto.request.UpdateProductRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.ProductResponse;
import com.cx.visionvibebe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> findAllProductBySubCategoryName(@RequestParam String subCategoryName, @RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productService.findAllProductByProductSubCategoryName(subCategoryName, page, size))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> addNewProduct(@ModelAttribute CreateNewProductRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createNewProduct(request))
                .build();
    }

    @GetMapping("name")
    public ApiResponse<ProductResponse> findProductByProductName(@RequestParam String productName) {
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

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProductByProductId(@PathVariable Long id, @ModelAttribute UpdateProductRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProductById(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ApiResponse.builder().build();
    }
}
