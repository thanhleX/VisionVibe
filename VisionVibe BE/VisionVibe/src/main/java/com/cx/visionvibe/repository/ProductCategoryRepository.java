package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.ProductCategory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Query("select a from ProductCategory a where a.isActive = true")
    List<ProductCategory> findAll();

    @Query("select a from ProductCategory a where a.id = ?1 and a.isActive = true")
    Optional<ProductCategory> findById(Long id);

    @Query("select a from ProductCategory a where a.name = ?1 and a.isActive = true")
    Optional<ProductCategory> findByName(String name);
}
