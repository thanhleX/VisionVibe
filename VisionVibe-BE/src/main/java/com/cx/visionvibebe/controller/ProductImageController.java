package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteImage(@PathVariable Long id) throws IOException {
        return ApiResponse.<String>builder()
                .result(productImageService.deleteImage(id))
                .build();
    }
}
