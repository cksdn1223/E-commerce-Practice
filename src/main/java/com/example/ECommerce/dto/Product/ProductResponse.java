package com.example.ECommerce.dto.Product;

import com.example.ECommerce.entity.Product;

public record ProductResponse(Long id, String name, int price, int stockQuantity) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStockQuantity());
    }
}