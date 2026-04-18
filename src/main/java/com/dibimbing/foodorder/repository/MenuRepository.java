package com.dibimbing.foodorder.repository;

import com.dibimbing.foodorder.entity.Menu;
import com.dibimbing.foodorder.enums.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategory(MenuCategory category);
    List<Menu> findByNameContainingIgnoreCase(String name);
}
