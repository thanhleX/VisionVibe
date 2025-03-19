package com.cx.visionvibebe.configuration;

import com.cx.visionvibebe.entity.Permission;
import com.cx.visionvibebe.entity.Role;
import com.cx.visionvibebe.entity.User;
import com.cx.visionvibebe.repository.PermissionRepository;
import com.cx.visionvibebe.repository.RoleRepository;
import com.cx.visionvibebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    // Danh sách permission
    private static final List<String> PERMISSIONS = List.of(
            "CREATE_BLOG", "READ_BLOG", "UPDATE_BLOG", "DELETE_BLOG",
            "CREATE_PRODUCT", "READ_PRODUCT", "UPDATE_PRODUCT", "DELETE_PRODUCT",
            "CREATE_PRODUCT_DETAIL", "READ_PRODUCT_DETAIL", "UPDATE_PRODUCT_DETAIL", "DELETE_PRODUCT_DETAIL",
            "CREATE_BLOG_CATEGORY", "READ_BLOG_CATEGORY", "UPDATE_BLOG_CATEGORY", "DELETE_BLOG_CATEGORY",
            "CREATE_PRODUCT_CATEGORY", "READ_PRODUCT_CATEGORY", "UPDATE_PRODUCT_CATEGORY", "DELETE_PRODUCT_CATEGORY",
            "CREATE_PRODUCT_SUB_CATEGORY", "READ_PRODUCT_SUB_CATEGORY", "UPDATE_PRODUCT_SUB_CATEGORY", "DELETE_PRODUCT_SUB_CATEGORY",
            "CREATE_ORDER", "READ_BILL", "UPDATE_BILL", "DELETE_BILL",
            "CREATE_ORDERS", "READ_ORDERS", "UPDATE_ORDERS", "DELETE_ORDERS",
            "CREATE_ORDER_DETAIL", "READ_ORDER_DETAIL", "UPDATE_ORDER_DETAIL", "DELETE_ORDER_DETAIL",
            "CREATE_NOTIFICATION", "READ_NOTIFICATION", "UPDATE_NOTIFICATION", "DELETE_NOTIFICATION",
            "CREATE_USER", "READ_USER", "UPDATE_USER", "DELETE_USER",
            "CONFIRM_ORDER", "DENY_ORDER"
    );

    // Danh sách role
    private static final List<String> ROLES = List.of("ADMIN", "MODERATOR", "SALE", "WRITER");

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            initPermissions(); // Khởi tạo permission
            initRoles();       // Khởi tạo role và phân quyền cho role
            initAdminAccount(); // Khởi tạo tài khoản admin
        };
    }

    // 👉 Khởi tạo permission
    private void initPermissions() {
        for (String permissionName : PERMISSIONS) {
            if (!permissionRepository.existsPermissionByPermissionName(permissionName)) {
                Permission permission = Permission.builder()
                        .permissionName(permissionName)
                        .build();
                permissionRepository.save(permission);
                log.info("✅ Created permission: {}", permissionName);
            }
        }
    }

    // 👉 Khởi tạo role và phân quyền cho từng role
    private void initRoles() {
        List<Permission> allPermissions = permissionRepository.findAll();

        // Lấy permission theo tên
        Set<Permission> blogPermissions = getPermissions(allPermissions,
                "CREATE_BLOG", "READ_BLOG", "UPDATE_BLOG", "DELETE_BLOG");

        Set<Permission> productPermissions = getPermissions(allPermissions,
                "CREATE_PRODUCT", "READ_PRODUCT", "UPDATE_PRODUCT", "DELETE_PRODUCT",
                "CREATE_PRODUCT_DETAIL", "READ_PRODUCT_DETAIL", "UPDATE_PRODUCT_DETAIL", "DELETE_PRODUCT_DETAIL");

        Set<Permission> blogCategoryPermissions = getPermissions(allPermissions,
                "CREATE_BLOG_CATEGORY", "READ_BLOG_CATEGORY", "UPDATE_BLOG_CATEGORY", "DELETE_BLOG_CATEGORY");

        Set<Permission> productCategoryPermissions = getPermissions(allPermissions,
                "CREATE_PRODUCT_CATEGORY", "READ_PRODUCT_CATEGORY", "UPDATE_PRODUCT_CATEGORY", "DELETE_PRODUCT_CATEGORY",
                "CREATE_PRODUCT_SUB_CATEGORY", "READ_PRODUCT_SUB_CATEGORY", "UPDATE_PRODUCT_SUB_CATEGORY", "DELETE_PRODUCT_SUB_CATEGORY");

        Set<Permission> orderPermissions = getPermissions(allPermissions,
                "CREATE_ORDER", "READ_BILL", "UPDATE_BILL", "DELETE_BILL",
                "CREATE_ORDERS", "READ_ORDERS", "UPDATE_ORDERS", "DELETE_ORDERS",
                "CREATE_ORDER_DETAIL", "READ_ORDER_DETAIL", "UPDATE_ORDER_DETAIL", "DELETE_ORDER_DETAIL");

        Set<Permission> notificationPermissions = getPermissions(allPermissions,
                "CREATE_NOTIFICATION", "READ_NOTIFICATION", "UPDATE_NOTIFICATION", "DELETE_NOTIFICATION");

        Set<Permission> userPermissions = getPermissions(allPermissions,
                "CREATE_USER", "READ_USER", "UPDATE_USER", "DELETE_USER");

        Set<Permission> confirmPermissions = getPermissions(allPermissions,
                "CONFIRM_ORDER", "DENY_ORDER");

        // ✅ ADMIN: Có tất cả các quyền
        createRole("ADMIN", new HashSet<>(allPermissions));

        // ✅ MODERATOR: Quản lý bài viết, thông báo, người dùng
        createRole("MODERATOR", new HashSet<>());
        addPermissionsToRole("MODERATOR", blogPermissions);
        addPermissionsToRole("MODERATOR", blogCategoryPermissions);
        addPermissionsToRole("MODERATOR", notificationPermissions);
        addPermissionsToRole("MODERATOR", userPermissions);

        // ✅ SALE: Quản lý sản phẩm, đơn hàng, xác nhận đơn hàng
        createRole("SALE", new HashSet<>());
        addPermissionsToRole("SALE", productPermissions);
        addPermissionsToRole("SALE", productCategoryPermissions);
        addPermissionsToRole("SALE", orderPermissions);
        addPermissionsToRole("SALE", confirmPermissions);

        // ✅ WRITER: Quản lý bài viết
        createRole("WRITER", new HashSet<>());
        addPermissionsToRole("WRITER", blogPermissions);
        addPermissionsToRole("WRITER", blogCategoryPermissions);
    }

    // 👉 Lấy permission từ tên
    private Set<Permission> getPermissions(List<Permission> allPermissions, String... names) {
        Set<Permission> result = new HashSet<>();
        for (String name : names) {
            allPermissions.stream()
                    .filter(p -> p.getPermissionName().equals(name))
                    .findFirst()
                    .ifPresent(result::add);
        }
        return result;
    }

    // 👉 Tạo role mới
    private void createRole(String roleName, Set<Permission> permissions) {
        if (!roleRepository.existsRoleByRoleName(roleName)) {
            Role role = Role.builder()
                    .roleName(roleName)
                    .permissions(permissions)
                    .build();
            roleRepository.save(role);
            log.info("✅ Created role: {}", roleName);
        }
    }

    // 👉 Gán quyền cho role
    private void addPermissionsToRole(String roleName, Set<Permission> permissions) {
        Role role = roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

        role.getPermissions().addAll(permissions);
        roleRepository.save(role);
        log.info("✅ Added permissions to role: {}", roleName);
    }

    // 👉 Khởi tạo tài khoản admin
    private void initAdminAccount() {
        if (!userRepository.existsUserByUsername("admin")) {
            Role adminRole = roleRepository.findRoleByRoleName("ADMIN").orElseThrow(() ->
                    new RuntimeException("Admin role not found"));

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123456"))
                    .email("admin@example.com")
                    .fullName("Admin")
                    .isActive(true)
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);
            log.info("✅ Created admin account");
        } else {
            log.warn("⚠️ Admin account already exists");
        }
    }
}
