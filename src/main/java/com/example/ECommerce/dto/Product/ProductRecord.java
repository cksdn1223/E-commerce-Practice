package com.example.ECommerce.dto.Product;

import com.example.ECommerce.dto.Order.OrderResponse;
import com.example.ECommerce.dto.OrderItem.OrderItemDto;
import com.example.ECommerce.entity.Product;

import java.util.List;

public record ProductRecord(String name, int price, int stockQuantity) {
    public static ProductRecord from(Product product) {
        return new ProductRecord(product.getName(), product.getPrice(), product.getStockQuantity());
    }
}
