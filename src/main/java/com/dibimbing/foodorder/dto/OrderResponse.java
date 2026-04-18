package com.dibimbing.foodorder.dto;

import com.dibimbing.foodorder.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
