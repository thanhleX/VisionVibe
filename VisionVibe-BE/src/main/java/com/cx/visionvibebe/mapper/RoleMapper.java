package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.RoleDto;
import com.cx.visionvibebe.dto.response.RoleResponse;
import com.cx.visionvibebe.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDto(Role role);

    RoleResponse toRoleResponse(Role role);
}
