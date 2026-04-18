package com.dibimbing.foodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReportDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SalesReportResponse {
        private String period;
        private Double totalRevenue;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopSellingResponse {
        private String menuName;
        private Long totalQuantity;
    }
}
