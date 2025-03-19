package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.UserDto;
import com.cx.visionvibebe.dto.request.CreateNewUserRequest;
import com.cx.visionvibebe.dto.request.UpdateUserRequest;
import com.cx.visionvibebe.dto.response.UserResponse;
import com.cx.visionvibebe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    UserResponse toUserResponse(User user);

    User toUser(CreateNewUserRequest request);

    User toUser(UpdateUserRequest request);

    void updateUser(UpdateUserRequest request, @MappingTarget User user);

}
