package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.CreateNewUserRequest;
import com.cx.visionvibebe.dto.request.UpdateUserRequest;
import com.cx.visionvibebe.dto.response.UserResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserResponse> findAllUser();

    UserResponse findUserById(Long id);

    UserResponse addNewUser(CreateNewUserRequest request) throws IOException;

    UserResponse updateUser(UpdateUserRequest request, Long id) throws IOException;

    void deleteUser(Long id);
}
