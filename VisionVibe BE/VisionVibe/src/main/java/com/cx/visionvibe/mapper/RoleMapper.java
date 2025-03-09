package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.RoleDto;
import com.cx.visionvibe.dto.request.RoleCreationRequest;
import com.cx.visionvibe.dto.response.RoleResponse;
import com.cx.visionvibe.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreationRequest request);

    RoleDto toRoleDto(Role role);

    RoleResponse toRoleResponse(Role role);
}
