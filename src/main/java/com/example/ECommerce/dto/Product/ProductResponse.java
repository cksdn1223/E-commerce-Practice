package com.example.ECommerce.dto.Product;

import com.example.ECommerce.entity.Product;

public record ProductResponse(Long id, String name, int price, int stockQuantity) { }