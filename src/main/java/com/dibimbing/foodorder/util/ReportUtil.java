package com.dibimbing.foodorder.util;

import com.dibimbing.foodorder.entity.Order;
import com.dibimbing.foodorder.entity.OrderItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static byte[] buildTextReport(List<Order> orders, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================================================================\n");
        sb.append(String.format("%-78s \n", title));
        sb.append("================================================================================\n\n");

        sb.append("1. DETAILED ORDERS\n");
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(String.format("%-10s | %-16s | %-12s | %-35s | %-10s\n", "Order ID", "Date", "Customer", "Items",
                "Total"));
        sb.append("--------------------------------------------------------------------------------\n");

        for (Order order : orders) {
            String itemsSummary = order.getOrderItems().stream()
                    .map(oi -> oi.getMenu().getName() + " (x" + oi.getQuantity() + ")")
                    .collect(Collectors.joining(", "));

            if (itemsSummary.length() > 33) {
                itemsSummary = itemsSummary.substring(0, 30) + "...";
            }

            sb.append(String.format("%-10d | %-16s | %-12s | %-35s | %-10.2f\n",
                    order.getId(),
                    order.getCreatedAt().format(DateTimeFormatter.ofPattern("MM-dd HH:mm")),
                    order.getUser().getUsername(),
                    itemsSummary,
                    order.getTotalPrice()));
        }

        double grandTotal = orders.stream().mapToDouble(Order::getTotalPrice).sum();
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(String.format("%-68s | %-10.2f\n", "GRAND TOTAL REVENUE", grandTotal));
        sb.append("================================================================================\n\n");

        // Aggregated Sales Report
        sb.append("2. SALES SUMMARY BY ITEM\n");
        sb.append("--------------------------------------------------------------------------------\n");
        sb.append(String.format("%-40s | %-15s | %-20s\n", "Menu Name", "Total Qty", "Total Revenue"));
        sb.append("--------------------------------------------------------------------------------\n");

        Map<String, ItemMetrics> metricsMap = calculateMetrics(orders);
        for (Map.Entry<String, ItemMetrics> entry : metricsMap.entrySet()) {
            sb.append(String.format("%-40s | %-15d | %-20.2f\n",
                    entry.getKey(),
                    entry.getValue().quantity,
                    entry.getValue().revenue));
        }
        sb.append("--------------------------------------------------------------------------------\n");

        return sb.toString().getBytes();
    }

    public static byte[] buildHtmlReport(List<Order> orders, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><style>")
                .append("body { font-family: 'Segoe UI', Arial, sans-serif; margin: 40px; color: #333; line-height: 1.6; }")
                .append("h1 { color: #2c3e50; text-align: center; border-bottom: 3px solid #3498db; padding-bottom: 20px; }")
                .append("h2 { color: #2980b9; margin-top: 40px; border-left: 5px solid #3498db; padding-left: 10px; }")
                .append("table { width: 100%; border-collapse: collapse; margin-top: 15px; background: white; box-shadow: 0 5px 15px rgba(0,0,0,0.08); }")
                .append("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }")
                .append("th { background-color: #3498db; color: white; text-transform: uppercase; font-size: 13px; letter-spacing: 1px; }")
                .append("tr:nth-child(even) { background-color: #f9f9f9; }")
                .append(".grand-total { font-weight: bold; background-color: #2c3e50 !important; color: white; }")
                .append(".item-list { font-size: 0.9em; color: #555; font-style: italic; }")
                .append(".footer { margin-top: 50px; text-align: center; font-size: 12px; color: #bdc3c7; }")
                .append(".revenue { font-family: 'Courier New', monospace; font-weight: bold; }")
                .append("</style></head><body>")
                .append("<h1>").append(title).append("</h1>")

                .append("<h2>1. Detailed Order History</h2>")
                .append("<table><thead><tr>")
                .append("<th>ID</th><th>Date</th><th>Customer</th><th>Items</th><th style=\"text-align:right\">Total Price</th>")
                .append("</tr></thead><tbody>");

        for (Order order : orders) {
            String items = order.getOrderItems().stream()
                    .map(oi -> oi.getMenu().getName() + " (x" + oi.getQuantity() + ")")
                    .collect(Collectors.joining("<br>"));

            sb.append("<tr>")
                    .append("<td>").append(order.getId()).append("</td>")
                    .append("<td>").append(order.getCreatedAt().format(DATE_FORMATTER)).append("</td>")
                    .append("<td>").append(order.getUser().getUsername()).append("</td>")
                    .append("<td class=\"item-list\">").append(items).append("</td>")
                    .append("<td style=\"text-align:right\" class=\"revenue\">Rp ")
                    .append(String.format("%,.0f", order.getTotalPrice())).append("</td>")
                    .append("</tr>");
        }

        double grandTotal = orders.stream().mapToDouble(Order::getTotalPrice).sum();
        sb.append("</tbody><tfoot><tr class=\"grand-total\">")
                .append("<td colspan=\"4\" style=\"text-align: right;\">TOTAL PERIOD REVENUE</td>")
                .append("<td style=\"text-align:right\">Rp ").append(String.format("%,.0f", grandTotal))
                .append("</td>")
                .append("</tr></tfoot></table>")

                .append("<h2>2. Sales Summary by Item</h2>")
                .append("<table><thead><tr>")
                .append("<th>Menu Name</th><th style=\"text-align:center\">Total Qty Sold</th><th style=\"text-align:right\">Total Item Revenue</th>")
                .append("</tr></thead><tbody>");

        Map<String, ItemMetrics> metricsMap = calculateMetrics(orders);
        for (Map.Entry<String, ItemMetrics> entry : metricsMap.entrySet()) {
            sb.append("<tr>")
                    .append("<td>").append(entry.getKey()).append("</td>")
                    .append("<td style=\"text-align:center\">").append(entry.getValue().quantity).append("</td>")
                    .append("<td style=\"text-align:right\" class=\"revenue\">Rp ")
                    .append(String.format("%,.0f", entry.getValue().revenue)).append("</td>")
                    .append("</tr>");
        }

        sb.append("</tbody></table>")
                .append("<div class=\"footer\">Report Generated on ")
                .append(LocalDateTime.now().format(DATE_FORMATTER)).append("<br>Food Order System v1.0</div>")
                .append("</body></html>");

        return sb.toString().getBytes();
    }

    private static Map<String, ItemMetrics> calculateMetrics(List<Order> orders) {
        Map<String, ItemMetrics> metrics = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem item : order.getOrderItems()) {
                String menuName = item.getMenu().getName();
                ItemMetrics m = metrics.getOrDefault(menuName, new ItemMetrics());
                m.quantity += item.getQuantity();
                m.revenue += (item.getQuantity() * item.getPriceAtBuy());
                metrics.put(menuName, m);
            }
        }
        return metrics;
    }

    private static class ItemMetrics {
        int quantity = 0;
        double revenue = 0.0;
    }
}
