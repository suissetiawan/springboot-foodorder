package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.SalesReportResponse;
import com.dibimbing.foodorder.dto.TopSellingResponse;
import com.dibimbing.foodorder.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales/daily")
    public ResponseEntity<SalesReportResponse> getDailySales() {
        return ResponseEntity.ok(reportService.getDailySalesReport());
    }

    @GetMapping("/sales/monthly")
    public ResponseEntity<SalesReportResponse> getMonthlySales() {
        return ResponseEntity.ok(reportService.getMonthlySalesReport());
    }

    @GetMapping("/top-selling")
    public ResponseEntity<List<TopSellingResponse>> getTopSelling() {
        return ResponseEntity.ok(reportService.getTopSellingItems());
    }
}
