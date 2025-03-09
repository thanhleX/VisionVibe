package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.FrameMaterialCreationRequest;
import com.cx.visionvibe.dto.request.FrameMaterialUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.FrameMaterialResponse;
import com.cx.visionvibe.service.FrameMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/frame-material")
public class FrameMaterialController {
    private final FrameMaterialService frameMaterialService;

    @GetMapping
    public ApiResponse<List<FrameMaterialResponse>> findAllFrameMaterial() {
        return ApiResponse.<List<FrameMaterialResponse>>builder()
                .result(frameMaterialService.findAllFrameMaterial())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<FrameMaterialResponse> findFrameMaterialById(@PathVariable Long id) {
        return ApiResponse.<FrameMaterialResponse>builder()
                .result(frameMaterialService.findFrameMaterialById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<FrameMaterialResponse> createFrameMaterial(@RequestBody FrameMaterialCreationRequest request) {
        return ApiResponse.<FrameMaterialResponse>builder()
                .result(frameMaterialService.createFrameMaterial(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<FrameMaterialResponse> updateFrameMaterial(@PathVariable Long id, @RequestBody FrameMaterialUpdateRequest request) {
        return ApiResponse.<FrameMaterialResponse>builder()
                .result(frameMaterialService.updateFrameMaterial(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFrameMaterial(@PathVariable Long id) {
        frameMaterialService.deleteFrameMaterial(id);
        return ApiResponse.<Void>builder().build();
    }
}
