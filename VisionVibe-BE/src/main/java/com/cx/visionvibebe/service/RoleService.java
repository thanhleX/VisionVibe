package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewRoleRequest;
import com.cx.visionvibebe.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> findAllRole();

    RoleResponse addNewRole(CreateNewRoleRequest request);

    RoleResponse findRoleById(Long id);
}
