package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.FrameColorCreationRequest;
import com.cx.visionvibe.dto.request.FrameColorUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.FrameColorResponse;
import com.cx.visionvibe.service.FrameColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/frame-color")
public class FrameColorController {
    private final FrameColorService frameColorService;

    @GetMapping
    public ApiResponse<List<FrameColorResponse>> findAllFrameColor() {
        return ApiResponse.<List<FrameColorResponse>>builder()
                .result(frameColorService.findAllFrameColor())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<FrameColorResponse> findFrameColorById(@PathVariable Long id) {
        return ApiResponse.<FrameColorResponse>builder()
                .result(frameColorService.findFrameColorById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<FrameColorResponse> createFrameColor(@RequestBody FrameColorCreationRequest request) {
        return ApiResponse.<FrameColorResponse>builder()
                .result(frameColorService.createFrameColor(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<FrameColorResponse> updateFrameColor(@PathVariable Long id, @RequestBody FrameColorUpdateRequest request) {
        return ApiResponse.<FrameColorResponse>builder()
                .result(frameColorService.updateFrameColor(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFrameColor(@PathVariable Long id) {
        frameColorService.deleteFrameColor(id);
        return ApiResponse.<Void>builder().build();
    }
}
