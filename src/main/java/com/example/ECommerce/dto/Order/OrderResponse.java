package com.example.ECommerce.dto.Order;

import com.example.ECommerce.entity.Order;
import com.example.ECommerce.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        LocalDateTime orderDate,
        List<OrderItemDto> orderItems
) {
    public static OrderResponse from(Order order) {
        List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                .map(OrderItemDto::from)
                .toList();
        return new OrderResponse(order.getId(), order.getOrderDate(), itemDtos);
    }
}