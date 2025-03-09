package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.PermissionDto;
import com.cx.visionvibe.dto.request.PermissionCreationRequest;
import com.cx.visionvibe.dto.response.PermissionResponse;
import com.cx.visionvibe.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreationRequest permission);

    PermissionResponse toPermissionResponse(Permission permission);

    PermissionDto toPermissionDto(Permission permission);
}
