package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.PermissionCreationRequest;
import com.cx.visionvibe.dto.response.PermissionResponse;
import com.cx.visionvibe.entity.Permission;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.PermissionMapper;
import com.cx.visionvibe.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll()
                .stream().map(this::toPermissionResponse).toList();
    }

    public PermissionResponse getPermissionById(Long id) {
        return toPermissionResponse(permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_ID_NOT_FOUND)));
    }

    public PermissionResponse createPermission(PermissionCreationRequest request) {
        if (permissionRepository.findPermissionByPermissionName(request.getPermissionName()).isPresent())
            throw new AppException(ErrorCode.PERMISSION_NAME_DUPLICATED);

        Permission permission = Permission.builder()
                .permissionName(request.getPermissionName())
                .description(request.getDescription())
                .build();

        return toPermissionResponse(permissionRepository.save(permission));
    }

    private PermissionResponse toPermissionResponse(Permission permission) {
        return permissionMapper.toPermissionResponse(permission);
    }
}
