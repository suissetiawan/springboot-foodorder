package com.dibimbing.foodorder.entity;

import com.dibimbing.foodorder.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price")
    private Double totalPrice;
}
