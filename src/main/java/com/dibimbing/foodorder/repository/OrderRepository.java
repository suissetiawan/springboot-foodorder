package com.dibimbing.foodorder.repository;

import com.dibimbing.foodorder.entity.Order;
import com.dibimbing.foodorder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.createdAt BETWEEN :start AND :end AND o.status IN ('PAID', 'COMPLETED')")
    Double calculateTotalSalesBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.user LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.menu WHERE o.createdAt BETWEEN :start AND :end AND o.status IN ('PAID', 'COMPLETED')")
    List<Order> findOrdersBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
