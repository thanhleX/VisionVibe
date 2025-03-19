package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewPermissionRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.PermissionResponse;
import com.cx.visionvibebe.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping()
    ApiResponse<List<PermissionResponse>> getAllPermission() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.findAllPermission())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<PermissionResponse> getPermissionById(@PathVariable Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.findPermissionById(id))
                .build();
    }

    @PostMapping()
    ApiResponse<PermissionResponse> addNewPermission(@RequestBody CreateNewPermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.addNewPermission(request))
                .build();
    }
}
