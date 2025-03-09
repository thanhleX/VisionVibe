package com.cx.visionvibe.dto.response;

import com.cx.visionvibe.dto.PermissionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class RoleResponse {
    private Long id;
    private String roleName;
    private String description;
    private Set<PermissionDto> permissionDtoSet;
}
