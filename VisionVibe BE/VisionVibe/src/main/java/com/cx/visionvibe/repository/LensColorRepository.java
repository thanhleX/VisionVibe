package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.LensColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LensColorRepository extends JpaRepository<LensColor, Long> {
    @Query("select a from LensColor a where a.id = ?1 and a.isActive = true")
    Optional<LensColor> findByLensColorId(Long id);

    @Query("select a from LensColor a where a.isActive = true")
    List<LensColor> findAll();
}
