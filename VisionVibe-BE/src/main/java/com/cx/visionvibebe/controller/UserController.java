package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.request.CreateNewUserRequest;
import com.cx.visionvibebe.dto.request.UpdateUserRequest;
import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.UserResponse;
import com.cx.visionvibebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ApiResponse<List<UserResponse>> findAllUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.findAllUser())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> findUserById(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.findUserById(id))
                .build();
    }

    @PostMapping()
    public ApiResponse<UserResponse> addNewUser(@ModelAttribute CreateNewUserRequest request) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.addNewUser(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUserById(@ModelAttribute UpdateUserRequest request, @PathVariable Long id) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(request, id))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.<UserResponse>builder()
                .build();
    }
}
