package com.dibimbing.foodorder.dto;

import com.dibimbing.foodorder.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderResponse {
        private Long id;
        private OrderStatus status;
        private Double totalPrice;
        private LocalDateTime createdAt;
        private List<OrderItemResponse> items;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemResponse {
        private Long menuId;
        private String menuName;
        private Integer quantity;
        private Double priceAtBuy;
    }
}
