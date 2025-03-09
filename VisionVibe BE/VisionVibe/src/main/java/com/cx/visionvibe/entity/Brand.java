package com.cx.visionvibe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "brands")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand implements UploadThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;

    @Column(name = "thumbnail_url")
    String thumbnailUrl;

    @Column(name = "thumbnail_public_id")
    String thumbnailPublicId;

    @Column(name = "is_active")
    Boolean isActive;

    @OneToMany(mappedBy = "brand")
    List<Product> products;
}
