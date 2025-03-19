package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("select a from Promotion a where a.isActive = true")
    List<Promotion> findAllPromotion();

    @Query("select a from Promotion a where a.id = ?1 and a.isActive = true")
    Optional<Promotion> findPromotionById(Long id);

    @Query("select a from Promotion a where a.name = ?1 and a.isActive = true ")
    Optional<Promotion> findPromotionByName(String name);
}
