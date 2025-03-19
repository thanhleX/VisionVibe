package com.cx.visionvibebe.dto.response;

import com.cx.visionvibebe.dto.PermissionDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private Long id;
    private String roleName;
    private List<PermissionDto> permissionDtoList;
}
