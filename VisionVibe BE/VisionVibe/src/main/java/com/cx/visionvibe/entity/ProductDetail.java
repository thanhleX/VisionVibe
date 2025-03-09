package com.cx.visionvibe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer stock;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "frame_color_id")
    FrameColor frameColor;

    @ManyToOne
    @JoinColumn(name = "lens_color_id")
    LensColor lensColor;

    @ManyToOne
    @JoinColumn(name = "frame_material_id")
    FrameMaterial frameMaterial;

    @ManyToOne
    @JoinColumn(name = "lens_material_id")
    LensMaterial lensMaterial;

    @OneToMany(mappedBy = "productDetail")
    List<ProductImage> productImages;

    @OneToMany(mappedBy = "productDetail")
    List<OrderDetail> orderDetails;
}
