package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.CartRequest;
import com.dibimbing.foodorder.dto.CartResponse;
import com.dibimbing.foodorder.entity.User;
import com.dibimbing.foodorder.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCart(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(cartService.getUserCart(user));
    }

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CartRequest request
    ) {
        return ResponseEntity.ok(cartService.addToCart(user, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponse> updateCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {
        return ResponseEntity.ok(cartService.updateCartItem(user, id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        cartService.removeCartItem(user, id);
        return ResponseEntity.noContent().build();
    }
}
