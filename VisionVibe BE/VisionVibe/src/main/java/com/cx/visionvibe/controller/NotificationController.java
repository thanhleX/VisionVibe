package com.cx.visionvibe.controller;

import com.cx.visionvibe.dto.response.ApiResponse;
import com.cx.visionvibe.dto.response.NotificationResponse;
import com.cx.visionvibe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ApiResponse<Page<NotificationResponse>> findAllNotificationsById(@PathVariable Long userId, @RequestParam int page, @RequestParam int size) {
        return ApiResponse.<Page<NotificationResponse>>builder()
                .result(notificationService.findAllNotificationByUserId(userId, page, size))
                .build();
    }

    @PutMapping("/set-to-read/{id}")
    public ApiResponse<Void> setReadNotification(@PathVariable Long id) {
        notificationService.setReadNotificationById(id);
        return ApiResponse.<Void>builder().build();
    }
}
