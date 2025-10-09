package com.example.ECommerce.dto.OrderItem;

import com.example.ECommerce.entity.OrderItem;

public record OrderItemRecord(
        Long productId,
        String productName,
        int orderPrice,
        int quantity
) {
    public static OrderItemRecord from(OrderItem orderItem) {
        return new OrderItemRecord(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getOrderPrice(), orderItem.getQuantity());
    }
}
