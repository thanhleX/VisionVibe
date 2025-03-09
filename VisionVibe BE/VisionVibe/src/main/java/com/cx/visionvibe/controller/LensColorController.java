package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.LensColorCreationRequest;
import com.cx.visionvibe.dto.request.LensColorUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.LensColorResponse;
import com.cx.visionvibe.service.LensColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lens-color")
public class LensColorController {
    private final LensColorService lensColorService;

    @GetMapping
    public ApiResponse<List<LensColorResponse>> findAllLensColor() {
        return ApiResponse.<List<LensColorResponse>>builder()
                .result(lensColorService.findAllLensColor())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LensColorResponse> findLensColorById(@PathVariable Long id) {
        return ApiResponse.<LensColorResponse>builder()
                .result(lensColorService.findLensColorById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<LensColorResponse> createLensColor(@RequestBody LensColorCreationRequest request) {
        return ApiResponse.<LensColorResponse>builder()
                .result(lensColorService.createLensColor(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LensColorResponse> updateLensColor(@PathVariable Long id, @RequestBody LensColorUpdateRequest request) {
        return ApiResponse.<LensColorResponse>builder()
                .result(lensColorService.updateLensColor(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLensColor(@PathVariable Long id) {
        lensColorService.deleteLensColor(id);
        return ApiResponse.<Void>builder().build();
    }
}
