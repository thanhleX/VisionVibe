package com.cx.visionvibebe.mapper;

import com.cx.visionvibebe.dto.response.NotificationResponse;
import com.cx.visionvibebe.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toNotificationResponse(Notification notification);
}
