package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.dto.CartDTO;
import com.dibimbing.foodorder.entity.User;
import com.dibimbing.foodorder.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<CartDTO.CartResponse>>> getCart(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                BaseResponse.<List<CartDTO.CartResponse>>builder()
                        .message("Success Get Cart")
                        .data(cartService.getUserCart(user))
                        .build());
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CartDTO.CartResponse>> addToCart(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CartDTO.CartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<CartDTO.CartResponse>builder()
                        .message("Success Add To Cart")
                        .data(cartService.addToCart(user, request))
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CartDTO.CartResponse>> updateCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(
                BaseResponse.<CartDTO.CartResponse>builder()
                        .message("Success Update Cart")
                        .data(cartService.updateCartItem(user, id, quantity))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> removeFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        cartService.removeCartItem(user, id);
        return ResponseEntity.ok(
                BaseResponse.<Void>builder()
                        .message("Success Remove Cart")
                        .build());
    }
}
