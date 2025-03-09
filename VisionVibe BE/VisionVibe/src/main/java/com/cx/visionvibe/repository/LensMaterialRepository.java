package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.LensMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LensMaterialRepository extends JpaRepository<LensMaterial, Long> {
    @Query("select a from LensMaterial a where a.id = ?1 and a.isActive = true")
    Optional<LensMaterial> findByLensMaterialId(Long id);

    @Query("select a from LensMaterial a where a.isActive = true")
    List<LensMaterial> findAll();
}
