package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.ProductCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Query("select a from ProductCategory a where a.isActive = true")
    @NotNull
    Page<ProductCategory> findAll(@NotNull Pageable pageable);

    @Query("select a from ProductCategory a where a.isActive = true")
    @NotNull
    List<ProductCategory> findAll();

    @Query("select a from ProductCategory a where a.id = ?1 and a.isActive = true")
    @NotNull
    Optional<ProductCategory> findById(@NotNull Long id);
}
