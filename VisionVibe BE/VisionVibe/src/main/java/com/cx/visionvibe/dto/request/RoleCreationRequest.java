package com.cx.visionvibe.dto.request;

import lombok.Getter;

import java.util.Set;

@Getter
public class RoleCreationRequest {
    private String roleName;
    private String description;
    private Set<Long> permissionId;
}
