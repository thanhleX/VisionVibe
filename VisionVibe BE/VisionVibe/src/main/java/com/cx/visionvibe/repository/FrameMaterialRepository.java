package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.FrameMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FrameMaterialRepository extends JpaRepository<FrameMaterial,Long> {
    @Query("select a from FrameMaterial a where a.id = ?1 and a.isActive = true")
    Optional<FrameMaterial> findByFrameMaterialId(long id);

    @Query("select a from FrameMaterial a where a.isActive = true")
    List<FrameMaterial> findAll();
}
