package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewProductColorRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.ProductColorResponse;
import com.cx.visionvibebe.service.ProductColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("color")
@RequiredArgsConstructor
public class ProductColorController {
    private final ProductColorService productColorService;

    @GetMapping()
    public ApiResponse<List<ProductColorResponse>> findALlProductColors() {
        return ApiResponse.<List<ProductColorResponse>>builder()
                .result(productColorService.getAllColors())
                .build();
    }

    @PostMapping
    public ApiResponse<ProductColorResponse> addNewProductColor(@RequestBody CreateNewProductColorRequest request) {
        return ApiResponse.<ProductColorResponse>builder()
                .result(productColorService.addNewColor(request))
                .build();
    }
}
