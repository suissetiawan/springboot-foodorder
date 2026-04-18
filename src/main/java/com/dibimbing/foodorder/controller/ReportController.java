package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.dto.ReportDTO;
import com.dibimbing.foodorder.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales/daily")
    public ResponseEntity<BaseResponse<ReportDTO.SalesReportResponse>> getDailySales() {
        return ResponseEntity.ok(
                BaseResponse.<ReportDTO.SalesReportResponse>builder()
                        .message("Daily sales report retrieved")
                        .data(reportService.getDailySalesReport())
                        .build()
        );
    }

    @GetMapping("/sales/monthly")
    public ResponseEntity<BaseResponse<ReportDTO.SalesReportResponse>> getMonthlySales() {
        return ResponseEntity.ok(
                BaseResponse.<ReportDTO.SalesReportResponse>builder()
                        .message("Monthly sales report retrieved")
                        .data(reportService.getMonthlySalesReport())
                        .build()
        );
    }

    @GetMapping("/top-selling")
    public ResponseEntity<BaseResponse<List<ReportDTO.TopSellingResponse>>> getTopSelling() {
        return ResponseEntity.ok(
                BaseResponse.<List<ReportDTO.TopSellingResponse>>builder()
                        .message("Top selling items report retrieved")
                        .data(reportService.getTopSellingItems())
                        .build()
        );
    }
}
