package com.dibimbing.foodorder.service;

import com.dibimbing.foodorder.dto.OrderItemResponse;
import com.dibimbing.foodorder.dto.OrderResponse;
import com.dibimbing.foodorder.entity.*;
import com.dibimbing.foodorder.enums.OrderStatus;
import com.dibimbing.foodorder.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderResponse checkout(User user) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 1. Validation: check stock
        for (Cart cartItem : cartItems) {
            Menu menu = cartItem.getMenu();
            if (menu.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + menu.getName());
            }
        }

        // 2. Calculate Total
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getMenu().getPrice() * item.getQuantity())
                .sum();

        // 3. Create Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PAID); // Assuming PAID for simplicity after checkout
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);

        // 4. Create OrderItems and Update Menu Stock
        for (Cart cartItem : cartItems) {
            Menu menu = cartItem.getMenu();
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setMenu(menu);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtBuy(menu.getPrice());
            orderItemRepository.save(orderItem);

            // Update Stock
            menu.setStock(menu.getStock() - cartItem.getQuantity());
            menuRepository.save(menu);
        }

        // 5. Clear Cart
        cartRepository.deleteAll(cartItems);

        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrderHistory(User user) {
        return orderRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> items = orderItemRepository.findByOrder(order).stream()
                .map(item -> OrderItemResponse.builder()
                        .menuId(item.getMenu().getId())
                        .menuName(item.getMenu().getName())
                        .quantity(item.getQuantity())
                        .priceAtBuy(item.getPriceAtBuy())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}
