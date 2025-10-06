package com.example.ECommerce.dto.Order;

import java.util.List;

public record OrderRequest(List<OrderItemRequest> orderItems) {
}
