package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select a from User a where a.username = ?1 and a.isActive = true")
    Optional<User> findUserByUsername(String username);

    @Query("select a from User a where a.id = ?1 and a.isActive = true")
    Optional<User> findById(Long id);

    @Query("select a from User a where a.isActive = true ")
    List<User> findAll();

    boolean existsUserByUsername(String username);

    boolean existsUsersByEmail(String email);
}
