package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.PermissionDto;
import com.cx.visionvibe.dto.request.RoleCreationRequest;
import com.cx.visionvibe.dto.response.RoleResponse;
import com.cx.visionvibe.entity.Permission;
import com.cx.visionvibe.entity.Role;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.PermissionMapper;
import com.cx.visionvibe.mapper.RoleMapper;
import com.cx.visionvibe.repository.PermissionRepository;
import com.cx.visionvibe.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    RoleMapper roleMapper;
    PermissionMapper permissionMapper;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream().map(this::toRoleResponse).toList();
    }

    public RoleResponse getRoleById(Long id) {
        return toRoleResponse(roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_ID_NOT_FOUND)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public RoleResponse createRole(RoleCreationRequest request) {
        if (roleRepository.findByRoleName(request.getRoleName()).isPresent())
            throw new AppException(ErrorCode.ROLE_NAME_DUPLICATED);

        Set<Permission> permissions = request.getPermissionId().stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_ID_NOT_FOUND)))
                .collect(Collectors.toSet());

        Role role = Role.builder()
                .roleName(request.getRoleName())
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        return toRoleResponse(roleRepository.save(role));
    }

    private RoleResponse toRoleResponse(Role role) {
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        Set<PermissionDto> permissionDtoSet = role.getPermissions().stream()
                .map(permissionMapper::toPermissionDto).collect(Collectors.toSet());
        roleResponse.setPermissionDtoSet(permissionDtoSet);
        return roleResponse;
    }
}
