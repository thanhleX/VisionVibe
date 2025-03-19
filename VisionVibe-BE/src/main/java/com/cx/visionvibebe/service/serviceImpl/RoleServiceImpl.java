package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.PermissionDto;
import com.cx.visionvibebe.dto.request.CreateNewRoleRequest;
import com.cx.visionvibebe.dto.response.RoleResponse;
import com.cx.visionvibebe.entity.Permission;
import com.cx.visionvibebe.entity.Role;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.mapper.PermissionMapper;
import com.cx.visionvibebe.mapper.RoleMapper;
import com.cx.visionvibebe.repository.PermissionRepository;
import com.cx.visionvibebe.repository.RoleRepository;
import com.cx.visionvibebe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','SALE')")
    public List<RoleResponse> findAllRole() {
        return roleRepository.findAll().stream().map(this::toRoleResponse).toList();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RoleResponse addNewRole(CreateNewRoleRequest request) {
        if (roleRepository.findRoleByRoleName(request.getRoleName()).isPresent()) {
            throw new AppException(ErrorCode.ROLE_NAME_DUPLICATED);
        }
        Set<Permission> permissions = request.getPermissionId().stream().map(id -> permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_ID_NOT_FOUND))).collect(Collectors.toSet());
        Role role = Role.builder()
                .roleName(request.getRoleName())
                .permissions(permissions)
                .build();
        return toRoleResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse findRoleById(Long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_ID_NOT_FOUND));
        return toRoleResponse(role);
    }

    private RoleResponse toRoleResponse(Role role) {
        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        List<PermissionDto> permissionDtoList = role.getPermissions().stream().map(permissionMapper::toPermissionDto).toList();
        roleResponse.setPermissionDtoList(permissionDtoList);
        return roleResponse;
    }
}
