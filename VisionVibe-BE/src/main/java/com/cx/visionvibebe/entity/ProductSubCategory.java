package com.cx.visionvibebe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_product_categories")
public class ProductSubCategory implements UploadThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name ="thumbnail_url")
    private String thumbnailUrl;

    @Column(name ="thumbnail_public_id")
    private String thumbnailPublicId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "productSubCategory")
    private List<Product> products;
}