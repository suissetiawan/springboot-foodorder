package com.dibimbing.foodorder.entity;

import com.dibimbing.foodorder.enums.MenuCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menus")
@Setter
@Getter
public class Menu extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    @Enumerated(EnumType.STRING)
    private MenuCategory category;
    private Integer stock;

    @Column(name = "image_url")
    private String imageUrl;
}