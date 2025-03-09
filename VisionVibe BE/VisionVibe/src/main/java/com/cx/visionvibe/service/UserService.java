package com.cx.visionvibe.service;

import com.cx.visionvibe.constant.PredefinedRole;
import com.cx.visionvibe.dto.request.UserCreationRequest;
import com.cx.visionvibe.dto.request.UserUpdateRequest;
import com.cx.visionvibe.dto.response.UserResponse;
import com.cx.visionvibe.entity.Role;
import com.cx.visionvibe.entity.User;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.RoleMapper;
import com.cx.visionvibe.mapper.UserMapper;
import com.cx.visionvibe.repository.RoleRepository;
import com.cx.visionvibe.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    UserMapper userMapper;
    RoleMapper roleMapper;

    UploadService uploadService;

    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse).toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public UserResponse getUserById(Long id) {
        return toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND)));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public UserResponse createUser(UserCreationRequest request) throws IOException {
        if (!request.getPassword().equals(request.getRePassword()))
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);

        if (userRepository.existsUserByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USERNAME_DUPLICATED);

        if (userRepository.existsUsersByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_DUPLICATED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        uploadService.uploadThumbnail(user, request.getImage());

        Set<Role> roles = request.getRoleId().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_ID_NOT_FOUND)))
                .collect(Collectors.toSet());

        user.setRoles(new HashSet<>(roles));

        return toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public UserResponse updateUser(Long id, UserUpdateRequest request) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND));

        userMapper.updateUser(user, request);
        user.setUpdatedAt(LocalDateTime.now());

        Set<Role> roles = request.getRoleId().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_ID_NOT_FOUND)))
                .collect(Collectors.toSet());

        user.setRoles(new HashSet<>(roles));

        if (request.getImage() != null && !request.getImage().isEmpty())
            uploadService.uploadThumbnail(user, request.getImage());

        return toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND));

        user.setIsActive(false);
        userRepository.save(user);
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = userMapper.toUserResponse(user);
        response.setRoleDtos(user.getRoles().stream().map(roleMapper::toRoleDto).collect(Collectors.toSet()));
        return response;
    }
}
