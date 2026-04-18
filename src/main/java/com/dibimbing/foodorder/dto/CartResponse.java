package com.dibimbing.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private Long menuId;
    private String menuName;
    private Double price;
    private Integer quantity;
    private Double subTotal;
}
