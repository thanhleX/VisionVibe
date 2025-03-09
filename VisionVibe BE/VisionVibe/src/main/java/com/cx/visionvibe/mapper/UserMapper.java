package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.request.UserCreationRequest;
import com.cx.visionvibe.dto.request.UserUpdateRequest;
import com.cx.visionvibe.dto.response.UserResponse;
import com.cx.visionvibe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
