package com.dibimbing.foodorder.util;

import com.dibimbing.foodorder.entity.Order;
import com.dibimbing.foodorder.entity.OrderItem;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ReportUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static byte[] buildPdfReport(List<Order> orders, String title) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Styling
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(52, 152, 219));
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            Font smallItalicFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, Color.GRAY);

            // Title
            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            titlePara.setSpacingAfter(20);
            document.add(titlePara);

            // 1. Detailed Orders
            Paragraph section1 = new Paragraph("1. Detailed Order History", sectionFont);
            section1.setSpacingAfter(10);
            document.add(section1);

            PdfPTable table1 = new PdfPTable(5);
            table1.setWidthPercentage(100);
            table1.setWidths(new float[] { 1, 2, 2, 4, 1.5f });

            addTableHeader(table1, headerFont, new String[] { "ID", "Date", "Customer", "Items", "Total Price" });

            for (Order order : orders) {
                String items = order.getOrderItems().stream()
                        .map(oi -> oi.getMenu().getName() + " (x" + oi.getQuantity() + ")")
                        .collect(Collectors.joining(", "));

                table1.addCell(createCell(String.valueOf(order.getId()), normalFont));
                table1.addCell(createCell(order.getCreatedAt().format(DATE_FORMATTER), normalFont));
                table1.addCell(createCell(order.getUser().getUsername(), normalFont));
                table1.addCell(createCell(items, smallItalicFont));
                table1.addCell(createCell(String.format("Rp %,.0f", order.getTotalPrice()), normalFont, Element.ALIGN_RIGHT));
            }

            // Grand Total Row
            double grandTotal = orders.stream().mapToDouble(Order::getTotalPrice).sum();
            PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL PERIOD REVENUE", headerFont));
            totalLabelCell.setColspan(4);
            totalLabelCell.setBackgroundColor(new Color(44, 62, 80));
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setPadding(8);
            table1.addCell(totalLabelCell);

            PdfPCell totalValCell = new PdfPCell(new Phrase(String.format("Rp %,.0f", grandTotal), headerFont));
            totalValCell.setBackgroundColor(new Color(44, 62, 80));
            totalValCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValCell.setPadding(8);
            table1.addCell(totalValCell);

            document.add(table1);

            // 2. Sales Summary
            document.add(new Paragraph("\n"));
            Paragraph section2 = new Paragraph("2. Sales Summary by Item", sectionFont);
            section2.setSpacingAfter(10);
            document.add(section2);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidthPercentage(100);
            table2.setWidths(new float[] { 4, 2, 2.5f });

            addTableHeader(table2, headerFont, new String[] { "Menu Name", "Total Qty Sold", "Total Item Revenue" });

            Map<String, ItemMetrics> metricsMap = calculateMetrics(orders);
            for (Map.Entry<String, ItemMetrics> entry : metricsMap.entrySet()) {
                table2.addCell(createCell(entry.getKey(), normalFont));
                table2.addCell(createCell(String.valueOf(entry.getValue().quantity), normalFont, Element.ALIGN_CENTER));
                table2.addCell(createCell(String.format("Rp %,.0f", entry.getValue().revenue), normalFont, Element.ALIGN_RIGHT));
            }

            document.add(table2);

            // Footer
            Paragraph footer = new Paragraph("\n\nReport Generated on " + LocalDateTime.now().format(DATE_FORMATTER) + "\nFood Order System v1.0", smallItalicFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private static void addTableHeader(PdfPTable table, Font font, String[] headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setBackgroundColor(new Color(52, 152, 219));
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        return cell;
    }

    private static PdfPCell createCell(String text, Font font, int alignment) {
        PdfPCell cell = createCell(text, font);
        cell.setHorizontalAlignment(alignment);
        return cell;
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
