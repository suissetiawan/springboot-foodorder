package com.dibimbing.foodorder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CartDTO {

    @Data
    public static class CartRequest {
        @NotNull
        private Long menuId;
        @NotNull
        @Min(1)
        private Integer quantity;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartResponse {
        private Long id;
        private Long menuId;
        private String menuName;
        private Double price;
        private Integer quantity;
        private Double subTotal;
    }
}
