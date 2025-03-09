package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.LensMaterialCreationRequest;
import com.cx.visionvibe.dto.request.LensMaterialUpdateRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.LensMaterialResponse;
import com.cx.visionvibe.service.LensMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lens-material")
public class LensMaterialController {
    private final LensMaterialService lensMaterialService;

    @GetMapping
    public ApiResponse<List<LensMaterialResponse>> findAllLensMaterial() {
        return ApiResponse.<List<LensMaterialResponse>>builder()
                .result(lensMaterialService.findAllLensMaterial())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LensMaterialResponse> findLensMaterialById(@PathVariable Long id) {
        return ApiResponse.<LensMaterialResponse>builder()
                .result(lensMaterialService.findLensMaterialById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<LensMaterialResponse> createLensMaterial(@RequestBody LensMaterialCreationRequest request) {
        return ApiResponse.<LensMaterialResponse>builder()
                .result(lensMaterialService.createLensMaterial(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LensMaterialResponse> updateLensMaterial(@PathVariable Long id, @RequestBody LensMaterialUpdateRequest request) {
        return ApiResponse.<LensMaterialResponse>builder()
                .result(lensMaterialService.updateLensMaterial(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLensMaterial(@PathVariable Long id) {
        lensMaterialService.deleteLensMaterial(id);
        return ApiResponse.<Void>builder().build();
    }
}
