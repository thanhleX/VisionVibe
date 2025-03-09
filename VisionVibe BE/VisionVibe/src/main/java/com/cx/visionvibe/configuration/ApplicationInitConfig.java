package com.cx.visionvibe.configuration;

import com.cx.visionvibe.constant.PredefinedRole;
import com.cx.visionvibe.entity.Role;
import com.cx.visionvibe.entity.User;
import com.cx.visionvibe.repository.RoleRepository;
import com.cx.visionvibe.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    UserRepository userRepository;
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    static String DEFAULT_ADMIN_USERNAME = "admin";
    static String DEFAULT_ADMIN_PASSWORD = "admin";

    @Bean
    public ApplicationRunner applicationRunner() {
        log.info("🚀 Starting application initialization...");
        return args -> {
            if (userRepository.findUserByUsername(DEFAULT_ADMIN_USERNAME).isEmpty()) {
                log.info("🔹 Creating default roles...");

                roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.ROLE_MODERATOR)
                        .description("Moderator role")
                        .build());

                roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.ROLE_SALE)
                        .description("Sale role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.ROLE_ADMIN)
                        .description("Admin role")
                        .build());

                log.info("✅ Roles initialized successfully");

                var role = new HashSet<Role>();
                role.add(adminRole);

                log.info("🔹 Creating default admin account...");

                User user = User.builder()
                        .username(DEFAULT_ADMIN_USERNAME)
                        .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                        .fullName("Admin")
                        .phone("0912345678")
                        .email("admin@gmail.com")
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .roles(role)
                        .build();

                userRepository.save(user);
                log.warn("⚠️ Default admin account created: username='admin', password='admin'. Please change the password immediately!");
            } else {
                log.info("✅ Default admin account already exists. Skipping creation.");
            }
            log.info("🎉 Application initialization completed!");
        };
    }
}
