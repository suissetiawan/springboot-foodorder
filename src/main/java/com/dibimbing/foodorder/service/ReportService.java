package com.dibimbing.foodorder.service;

import com.dibimbing.foodorder.dto.ReportDTO;
import com.dibimbing.foodorder.entity.Order;
import com.dibimbing.foodorder.repository.OrderItemRepository;
import com.dibimbing.foodorder.repository.OrderRepository;
import com.dibimbing.foodorder.util.ReportUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public ReportDTO.SalesReportResponse getDailySalesReport(LocalDate date) {
        if (date == null)
            date = LocalDate.now();

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        Double total = orderRepository.calculateTotalSalesBetween(start, end);

        return ReportDTO.SalesReportResponse.builder()
                .period("Daily Report")
                .date(date.toString())
                .totalRevenue(total != null ? total : 0.0)
                .build();
    }

    public ReportDTO.SalesReportResponse getMonthlySalesReport(Integer month, Integer year) {
        LocalDate now = LocalDate.now();
        if (month == null)
            month = now.getMonthValue();
        if (year == null)
            year = now.getYear();

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDateTime start = firstDay.atStartOfDay();
        LocalDateTime end = firstDay.plusMonths(1).atStartOfDay().minusNanos(1);

        Double total = orderRepository.calculateTotalSalesBetween(start, end);
        String monthName = firstDay.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        return ReportDTO.SalesReportResponse.builder()
                .period("Monthly Report")
                .monthName(monthName)
                .year(year)
                .totalRevenue(total != null ? total : 0.0)
                .build();
    }

    public List<ReportDTO.TopSellingResponse> getTopSellingItems() {
        return orderItemRepository.findTopSellingItems().stream()
                .limit(5)
                .map(res -> ReportDTO.TopSellingResponse.builder()
                        .menuName((String) res.get("menuName"))
                        .totalQuantity((Long) res.get("totalQuantity"))
                        .build())
                .collect(Collectors.toList());
    }

    public byte[] generateDailyPDF(LocalDate date) {
        if (date == null)
            date = LocalDate.now();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Order> orders = orderRepository.findOrdersBetween(start, end);
        byte[] content = ReportUtil.buildPdfReport(orders, "Daily Sales Report - " + date);

        saveReportToFile("daily_report_" + date + ".pdf", content);
        return content;
    }

    public byte[] generateMonthlyPDF(Integer month, Integer year) {
        LocalDate now = LocalDate.now();
        if (month == null)
            month = now.getMonthValue();
        if (year == null)
            year = now.getYear();

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDateTime start = firstDay.atStartOfDay();
        LocalDateTime end = firstDay.plusMonths(1).atStartOfDay().minusNanos(1);

        List<Order> orders = orderRepository.findOrdersBetween(start, end);
        String monthName = firstDay.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        byte[] content = ReportUtil.buildPdfReport(orders, "Monthly Sales Report - " + monthName + " " + year);

        saveReportToFile("monthly_report_" + month + "_" + year + ".pdf", content);
        return content;
    }

    private void saveReportToFile(String filename, byte[] content) {
        try {
            Path reportDir = Paths.get("report");
            if (!Files.exists(reportDir)) {
                Files.createDirectories(reportDir);
            }
            Path filePath = reportDir.resolve(filename);
            Files.write(filePath, content);
            log.info("Report saved to: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to save report to file: {}", e.getMessage());
        }
    }
}
