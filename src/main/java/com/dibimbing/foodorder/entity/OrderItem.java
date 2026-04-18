package com.dibimbing.foodorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Table(name = "order_items")
@SQLDelete(sql = "UPDATE order_items SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private Integer quantity;

    @Column(name = "price_at_buy")
    private Double priceAtBuy;
}
