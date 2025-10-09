package com.example.ECommerce.dto.Order;

import com.example.ECommerce.dto.OrderItem.OrderItemRecord;
import com.example.ECommerce.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String username,
        LocalDateTime orderDate,
        List<OrderItemRecord> orderItems
) {
    public static OrderResponse from(Order order) {
        List<OrderItemRecord> itemDtos = order.getOrderItems().stream()
                .map(OrderItemRecord::from)
                .toList();
        return new OrderResponse(order.getId(), order.getAppUser().getUsername(), order.getOrderDate(), itemDtos);
    }
}