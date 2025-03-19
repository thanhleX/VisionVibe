package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findPermissionByPermissionName(String permissionName);

    boolean existsPermissionByPermissionName(String permissionName);
}
