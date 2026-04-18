package com.dibimbing.foodorder.service;

import com.dibimbing.foodorder.dto.CartRequest;
import com.dibimbing.foodorder.dto.CartResponse;
import com.dibimbing.foodorder.entity.Cart;
import com.dibimbing.foodorder.entity.Menu;
import com.dibimbing.foodorder.entity.User;
import com.dibimbing.foodorder.repository.CartRepository;
import com.dibimbing.foodorder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;

    public List<CartResponse> getUserCart(User user) {
        return cartRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartResponse addToCart(User user, CartRequest request) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        Cart cart = cartRepository.findByUserAndMenuId(user, request.getMenuId())
                .orElse(new Cart());

        if (cart.getId() == null) {
            cart.setUser(user);
            cart.setMenu(menu);
            cart.setQuantity(request.getQuantity());
        } else {
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
        }

        return mapToResponse(cartRepository.save(cart));
    }

    @Transactional
    public CartResponse updateCartItem(User user, Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        cart.setQuantity(quantity);
        return mapToResponse(cartRepository.save(cart));
    }

    @Transactional
    public void removeCartItem(User user, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        cartRepository.delete(cart);
    }

    private CartResponse mapToResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .menuId(cart.getMenu().getId())
                .menuName(cart.getMenu().getName())
                .price(cart.getMenu().getPrice())
                .quantity(cart.getQuantity())
                .subTotal(cart.getMenu().getPrice() * cart.getQuantity())
                .build();
    }
}
