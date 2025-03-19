package com.cx.visionvibebe.controller;

import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.dto.response.NotificationResponse;
import com.cx.visionvibebe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("set-to-read/{id}")
    public ApiResponse<?> setReadNotification(@PathVariable Long id) {
        notificationService.setReadNotificationById(id);
        return ApiResponse.builder().build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<Page<NotificationResponse>> findALlNotificationsByUserId(@PathVariable Long userId, @RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<NotificationResponse>>builder()
                .result(notificationService.findAllNotificationByUserId(userId, page, size))
                .build();
    }
}
