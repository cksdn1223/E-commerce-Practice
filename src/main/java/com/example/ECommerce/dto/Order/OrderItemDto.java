package com.example.ECommerce.dto.Order;

import com.example.ECommerce.entity.OrderItem;

public record OrderItemDto(
        Long productId,
        String productName,
        int orderPrice,
        int quantity
) {
    public static OrderItemDto from(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getOrderPrice(), orderItem.getQuantity());
    }
}
