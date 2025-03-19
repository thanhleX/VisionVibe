package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;

public interface NotificationService {

    void setReadNotificationById(Long id);


    Page<NotificationResponse> findAllNotificationByUserId(Long userId, int page, int size);
}
