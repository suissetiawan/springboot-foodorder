package com.dibimbing.foodorder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequest {
    @NotNull
    private Long menuId;
    
    @NotNull
    @Min(1)
    private Integer quantity;
}
