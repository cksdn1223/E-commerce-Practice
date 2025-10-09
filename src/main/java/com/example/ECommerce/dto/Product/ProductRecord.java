package com.example.ECommerce.dto.Product;

import com.example.ECommerce.entity.Product;

public record ProductRecord(String name, int price, int stockQuantity) {
    public static ProductRecord from(Product product) {
        return new ProductRecord(product.getName(), product.getPrice(), product.getStockQuantity());
    }
}
