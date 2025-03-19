package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.PermissionDto;
import com.cx.visionvibebe.dto.response.PermissionResponse;
import com.cx.visionvibebe.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);

    PermissionDto toPermissionDto(Permission permission);
}
