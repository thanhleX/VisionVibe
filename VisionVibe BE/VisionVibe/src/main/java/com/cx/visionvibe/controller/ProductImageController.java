package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ProductImageController {
    private final ProductImageService productImageService;

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteImage(@PathVariable Long id) throws IOException {
        return ApiResponse.<String>builder()
                .result(productImageService.deleteImage(id))
                .build();
    }
}
