package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.ProductSubCategory;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Long> {
    @Query("select a from ProductSubCategory a where a.productCategory.id =?1 and a.isActive = true")
    Page<ProductSubCategory> findAllProductSubCategoryByProductCategoryId(Long productCategoryId, Pageable pageable);

    @Query("select a from ProductSubCategory a where a.productCategory.name = ?1 and a.isActive = true")
    List<ProductSubCategory> findByCategoryName(String name);

    @Query("select a from ProductSubCategory a where a.productCategory.name =?1 and a.isActive = true")
    List<ProductSubCategory> findAllProductSubCategoryByProductCategoryName(String productCategoryName);

    @Query("select a from ProductSubCategory a where a.id = ?1 and a.isActive = true")
    @NotNull
    Optional<ProductSubCategory> findById(@NotNull Long id);
}
