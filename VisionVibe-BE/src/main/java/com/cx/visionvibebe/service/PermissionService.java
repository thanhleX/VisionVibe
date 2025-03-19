package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewPermissionRequest;
import com.cx.visionvibebe.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> findAllPermission();

    PermissionResponse findPermissionById(Long id);

    PermissionResponse addNewPermission(CreateNewPermissionRequest request);
}
