package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.OrderResponse;
import com.dibimbing.foodorder.entity.User;
import com.dibimbing.foodorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.checkout(user));
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getOrderHistory(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.getUserOrderHistory(user));
    }
}
