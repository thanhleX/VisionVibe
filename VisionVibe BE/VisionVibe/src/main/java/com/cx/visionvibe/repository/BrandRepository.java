package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select a from Brand a where a.id = ?1 and a.isActive = true")
    Optional<Brand> findByBrandId(Long id);

    @Query("select a from Brand a where a.name = ?1 and a.isActive = true")
    Optional<Brand> findByBrandName(String name);

    @Query("select a from Brand a where a.isActive = true")
    List<Brand> findAll();
}
