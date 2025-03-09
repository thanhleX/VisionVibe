package com.cx.visionvibe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionResponse {
    private Long id;
    private String permissionName;
    private String description;
}
