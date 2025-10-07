package com.example.ECommerce.dto.Order;

import com.example.ECommerce.dto.OrderItem.OrderItemDto;
import com.example.ECommerce.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String username,
        LocalDateTime orderDate,
        List<OrderItemDto> orderItems
) {
    public static OrderResponse from(Order order) {
        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(OrderItemDto::from)
                .toList();
        return new OrderResponse(order.getId(), order.getAppUser().getUsername(), order.getOrderDate(), itemDtos);
    }
}