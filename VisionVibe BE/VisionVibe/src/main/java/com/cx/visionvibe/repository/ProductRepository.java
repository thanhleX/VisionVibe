package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select a from Product a where a.productName = ?1 and a.isActive = true")
    Optional<Product> findByProductName(String productName);

    @Query("select a from Product a where a.id = ?1 and a.isActive = true")
    Optional<Product> findById(Long productId);

    @Query("select a from Product a where a.productCategory.name = ?1 and a.isActive = true")
    Page<Product> findAllProductsByProductCategoryNameWithPagination(String productCategoryName, Pageable pageable);

    @Query("select a from Product a where a.productCategory.id = ?1 and a.isActive = true")
    List<Product>findAllProductsByProductCategoryId(Long productCategoryId);
}
