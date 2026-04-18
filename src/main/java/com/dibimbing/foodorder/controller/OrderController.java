package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.dto.OrderDTO;
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
    public ResponseEntity<BaseResponse<OrderDTO.OrderResponse>> checkout(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                BaseResponse.<OrderDTO.OrderResponse>builder()
                        .message("Checkout successful")
                        .data(orderService.checkout(user))
                        .build()
        );
    }

    @GetMapping("/history")
    public ResponseEntity<BaseResponse<List<OrderDTO.OrderResponse>>> getOrderHistory(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                BaseResponse.<List<OrderDTO.OrderResponse>>builder()
                        .message("Order history retrieved successfully")
                        .data(orderService.getUserOrderHistory(user))
                        .build()
        );
    }
}
