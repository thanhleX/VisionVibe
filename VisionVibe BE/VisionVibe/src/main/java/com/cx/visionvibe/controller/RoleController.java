package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.request.RoleCreationRequest;
import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.RoleResponse;
import com.cx.visionvibe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getRoleById(@PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRoleById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleCreationRequest roleCreationRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(roleCreationRequest))
                .build();
    }
}
