package com.cx.visionvibebe.repository;

import com.cx.visionvibebe.entity.User;
import org.jetbrains.annotations.NotNull;
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
    @NotNull
    Optional<User> findById(@NotNull Long id);

    @Query("select a from User a where  a.isActive = true")
    @NotNull
    List<User> findAll();

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);
}
