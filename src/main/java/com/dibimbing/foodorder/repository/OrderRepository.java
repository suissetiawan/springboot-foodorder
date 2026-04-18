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

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.createdAt >= :startDate AND o.status = 'PAID'")
    Double calculateTotalSalesSince(@Param("startDate") LocalDateTime startDate);

    // We can add more aggregation queries here later for reports
}
