package com.cx.visionvibe.entity;

import com.cx.visionvibe.enums.NotificationStatus;
import com.cx.visionvibe.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    NotificationStatus notificationStatus;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "notification_roles",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
