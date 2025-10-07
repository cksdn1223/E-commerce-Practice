package com.example.ECommerce.dto.Order;

import com.example.ECommerce.dto.OrderItem.OrderItemRequest;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(@NotEmpty List<OrderItemRequest> orderItems) {
}
