package com.dibimbing.foodorder.dto;

import com.dibimbing.foodorder.enums.MenuCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MenuDTO {

    @Data
    public static class MenuRequest {
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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuResponse {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private MenuCategory category;
        private Integer stock;
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
