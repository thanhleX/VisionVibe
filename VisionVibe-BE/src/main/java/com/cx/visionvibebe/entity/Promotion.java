package com.cx.visionvibebe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "promotions")
public class Promotion implements UploadThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "thumbnail_public_id")
    private String thumbnailPublicId;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private List<Product> products;
}
