package com.cx.visionvibe.mapper;

import com.cx.visionvibe.dto.response.NotificationResponse;
import com.cx.visionvibe.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toNotificationResponse(Notification notification);
}
