package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.OrderNotificationDto;
import com.cx.visionvibe.dto.response.NotificationResponse;
import com.cx.visionvibe.entity.Notification;
import com.cx.visionvibe.entity.Order;
import com.cx.visionvibe.entity.Role;
import com.cx.visionvibe.enums.NotificationStatus;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.repository.NotificationRepository;
import com.cx.visionvibe.repository.RoleRepository;
import com.cx.visionvibe.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {
    @NonFinal
    @Value("${spring.mail.username}")
    String senderEmail;

    NotificationRepository notificationRepository;
    RoleRepository roleRepository;
    UserRepository userRepository;

    JavaMailSender javaMailSender;

    SimpMessagingTemplate simpMessagingTemplate;

    public void sendOrderMail(String to, String subject, String htmlContent, Long billId) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(String.format(htmlContent, billId), true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.CANT_SEND_MAIL);
        }
    }

    public void createNewNotificationWhenCreateNewOrder(Order order) {
        Set<String> roleName = Set.of("ADMIN", "MODERATOR", "SALE");
        Set<Role> roles = roleRepository.findAll().stream()
                .filter(role -> roleName.contains(role.getRoleName()))
                .collect(Collectors.toSet());

        Notification notification = Notification.builder()
                .order(order)
                .roles(roles)
                .createdAt(LocalDateTime.now())
                .orderStatus(order.getOrderStatus())
                .notificationStatus(NotificationStatus.NEW)
                .build();

        notificationRepository.save(notification);
    }

    public void sendNotificationByWebSocket(Order order) {
        OrderNotificationDto orderNotificationDto = OrderNotificationDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus().name())
                .build();
        simpMessagingTemplate.convertAndSend("/topic/notifications", orderNotificationDto);
    }

    public void setReadNotificationById(Long id) {
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_ID_NOT_FOUND));
        notification.setNotificationStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }

    public Page<NotificationResponse> findAllNotificationByUserId(Long userId, int page, int size) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Set<Role> roles = user.getRoles();

        Set<Notification> notificationSet = new HashSet<>();
        roles.forEach(role -> notificationSet.addAll(role.getNotifications()));

        List<Notification> notifications = new ArrayList<>(notificationSet);
        notifications.sort(Comparator.comparing(Notification::getCreatedAt));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), notifications.size());
        List<NotificationResponse> subList = notifications.subList(start, end)
                .stream()
                .map(this::toNotificationResponse)
                .toList();

        return new PageImpl<>(subList, pageable, notifications.size());
    }

    private NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .createdAt(notification.getCreatedAt())
                .orderStatus(notification.getOrderStatus().name())
                .notificationStatus(notification.getNotificationStatus().name())
                .orderId(notification.getOrder().getId())
                .build();
    }
}
