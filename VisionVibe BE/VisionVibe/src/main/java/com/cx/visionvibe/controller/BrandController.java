package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.BrandCreationRequest;
import com.cx.visionvibe.dto.request.BrandUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.BrandResponse;
import com.cx.visionvibe.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ApiResponse<List<BrandResponse>> findAllBrand() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.findAllBrand())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> findBrandById(@PathVariable Long id) {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.findByBrandId(id))
                .build();
    }

    @PostMapping
    public ApiResponse<BrandResponse> createBrand(@RequestBody BrandCreationRequest request) throws IOException {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createBrand(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandUpdateRequest request) throws IOException {
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updateBrand(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
