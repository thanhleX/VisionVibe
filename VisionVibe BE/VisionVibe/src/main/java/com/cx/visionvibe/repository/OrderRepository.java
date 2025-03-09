package com.cx.visionvibe.repository;

import com.cx.visionvibe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select a from Order a where a.createdAt between ?1 and ?2 and a.orderStatus = 'CONFIRMED'")
    List<Order> findOrderInMonth(LocalDateTime startOfMonth, LocalDateTime endOfMonth);
}
