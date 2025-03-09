package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    @Query("select a from ProductDetail a where a.product.productName = ?1 and a.isActive = true")
    List<ProductDetail> findProductDetailByProductName(String productName);

    @Query("select a from ProductDetail a where a.id = ?1 and a.isActive = true")
    Optional<ProductDetail> findById(Long id);
}
