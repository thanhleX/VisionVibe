package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @EntityGraph(attributePaths = {"permissions"})
    Optional<Role> findRoleByRoleName(String roleName);

    boolean existsRoleByRoleName(String roleName);
}
