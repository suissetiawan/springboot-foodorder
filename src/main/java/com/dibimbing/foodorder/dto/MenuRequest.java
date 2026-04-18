package com.dibimbing.foodorder.dto;

import com.dibimbing.foodorder.enums.MenuCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRequest {
    @NotBlank
    private String name;
    
    private String description;
    
    @NotNull
    @Min(0)
    private Double price;
    
    @NotNull
    private MenuCategory category;
    
    @NotNull
    @Min(0)
    private Integer stock;
    
    private String imageUrl;
}
