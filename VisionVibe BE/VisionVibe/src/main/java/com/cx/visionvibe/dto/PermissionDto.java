package com.cx.visionvibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionDto {
    Long id;
    String permissionName;
}
