package com.dibimbing.foodorder.repository;

import com.dibimbing.foodorder.entity.Order;
import com.dibimbing.foodorder.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);

    @Query("SELECT oi.menu.name as menuName, SUM(oi.quantity) as totalQuantity " +
           "FROM OrderItem oi GROUP BY oi.menu.id ORDER BY totalQuantity DESC")
    List<Map<String, Object>> findTopSellingItems();
}
