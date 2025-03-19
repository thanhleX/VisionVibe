package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewProductMaterialRequest;
import com.cx.visionvibebe.dto.request.UpdateProductMaterialRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.ProductMaterialResponse;
import com.cx.visionvibebe.service.ProductMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("material")
@RequiredArgsConstructor
public class ProductMaterialController {
    private final ProductMaterialService productMaterialService;

    @GetMapping
    public ApiResponse<List<ProductMaterialResponse>> findAllProductMaterial() {
        return ApiResponse.<List<ProductMaterialResponse>>builder()
                .result(productMaterialService.findAllProductMaterials())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductMaterialResponse> findProductMaterialById(@PathVariable Long id) {
        return ApiResponse.<ProductMaterialResponse>builder()
                .result(productMaterialService.findProductMaterialById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductMaterialResponse> addNewProductMaterial(CreateNewProductMaterialRequest request) {
        return ApiResponse.<ProductMaterialResponse>builder()
                .result(productMaterialService.addNewProductMaterial(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductMaterialResponse> updateProductMaterialById(@PathVariable Long id, UpdateProductMaterialRequest request) {
        return ApiResponse.<ProductMaterialResponse>builder()
                .result(productMaterialService.updateProductMaterialById(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteProductMaterialById(@PathVariable Long id) {
        productMaterialService.deleteProductMaterialById(id);
        return ApiResponse.builder().build();
    }
}
