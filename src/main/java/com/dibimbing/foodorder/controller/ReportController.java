package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.dto.ReportDTO;
import com.dibimbing.foodorder.enums.ReportType;
import com.dibimbing.foodorder.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily")
    public ResponseEntity<BaseResponse<ReportDTO.SalesReportResponse>> getDailySales(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                BaseResponse.<ReportDTO.SalesReportResponse>builder()
                        .message("Success Get Daily Sales Report")
                        .data(reportService.getDailySalesReport(date))
                        .build());
    }

    @GetMapping("/monthly")
    public ResponseEntity<BaseResponse<ReportDTO.SalesReportResponse>> getMonthlySales(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(
                BaseResponse.<ReportDTO.SalesReportResponse>builder()
                        .message("Success Get Monthly Sales Report")
                        .data(reportService.getMonthlySalesReport(month, year))
                        .build());
    }

    @GetMapping("/top-selling")
    public ResponseEntity<BaseResponse<List<ReportDTO.TopSellingResponse>>> getTopSelling() {
        return ResponseEntity.ok(
                BaseResponse.<List<ReportDTO.TopSellingResponse>>builder()
                        .message("Success Get Top Selling Items Report")
                        .data(reportService.getTopSellingItems())
                        .build());
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport(
            @RequestParam(defaultValue = "DAILY") ReportType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        byte[] pdfData;
        String filename;

        if (ReportType.MONTHLY.equals(type)) {
            pdfData = reportService.generateMonthlyPDF(month, year);
            filename = "monthly_report_" + (month != null ? month : "current") + "_"
                    + (year != null ? year : "current") + ".pdf";
        } else {
            pdfData = reportService.generateDailyPDF(date);
            filename = "daily_report_" + (date != null ? date : LocalDate.now()) + ".pdf";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(pdfData);
    }
}
