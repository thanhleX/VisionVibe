package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {
    @Query("select a from ProductMaterial a where a.id =?1")
    Optional<ProductMaterial> findByProductMaterialId(Long id);
}
