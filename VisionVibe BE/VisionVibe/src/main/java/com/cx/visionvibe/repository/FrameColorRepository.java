package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.FrameColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrameColorRepository extends JpaRepository<FrameColor, Long> {
    @Query("select a from FrameColor a where a.id = ?1 and a.isActive = true")
    Optional<FrameColor> findByFrameColorId(Long id);

    @Query("select a from FrameColor a where a.isActive = true")
    List<FrameColor> findAll();
}
