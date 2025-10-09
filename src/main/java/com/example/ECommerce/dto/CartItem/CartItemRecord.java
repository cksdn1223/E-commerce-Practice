package com.example.ECommerce.dto.CartItem;

import com.example.ECommerce.entity.CartItem;

public record CartItemRecord(String productName, int price, int quantity) {
    public static CartItemRecord from(CartItem cartItem){
        return new CartItemRecord(cartItem.getProduct().getName(), cartItem.getProduct().getPrice(), cartItem.getQuantity());
    }
}
