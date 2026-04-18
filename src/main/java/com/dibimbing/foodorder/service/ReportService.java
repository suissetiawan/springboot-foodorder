package com.dibimbing.foodorder.service;

import com.dibimbing.foodorder.dto.SalesReportResponse;
import com.dibimbing.foodorder.dto.TopSellingResponse;
import com.dibimbing.foodorder.repository.OrderItemRepository;
import com.dibimbing.foodorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public SalesReportResponse getDailySalesReport() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        Double total = orderRepository.calculateTotalSalesSince(startOfDay);
        return SalesReportResponse.builder()
                .period("Today")
                .totalRevenue(total != null ? total : 0.0)
                .build();
    }

    public SalesReportResponse getMonthlySalesReport() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                .withNano(0);
        Double total = orderRepository.calculateTotalSalesSince(startOfMonth);
        return SalesReportResponse.builder()
                .period("This Month")
                .totalRevenue(total != null ? total : 0.0)
                .build();
    }

    public List<TopSellingResponse> getTopSellingItems() {
        List<Map<String, Object>> results = orderItemRepository.findTopSellingItems();
        return results.stream()
                .map(res -> TopSellingResponse.builder()
                        .menuName((String) res.get("menuName"))
                        .totalQuantity((Long) res.get("totalQuantity"))
                        .build())
                .collect(Collectors.toList());
    }
}
