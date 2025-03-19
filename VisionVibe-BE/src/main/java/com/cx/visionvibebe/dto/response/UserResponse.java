package com.cx.visionvibebe.dto.response;

import com.cx.visionvibebe.dto.RoleDto;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Boolean isActive;
    private String thumbnailUrl;
    private String thumbnailPublicId;
    private Set<RoleDto> roleDtos;
}
