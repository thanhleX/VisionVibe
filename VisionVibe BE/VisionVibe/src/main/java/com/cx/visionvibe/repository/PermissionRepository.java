package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findPermissionByPermissionName(String permission);
}
