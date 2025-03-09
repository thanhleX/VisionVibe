package com.cx.visionvibe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lens_material")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LensMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(name = "is_active")
    Boolean isActive;
}
