package com.example.ECommerce.dto.Cart;

import com.example.ECommerce.dto.CartItem.CartItemRecord;
import com.example.ECommerce.entity.Cart;

import java.util.List;

public record CartRecord(String username, List<CartItemRecord> cartItem, int totalPrice) {
    public static CartRecord from(Cart cart) {
        List<CartItemRecord> items = cart.getCartItems().stream().map(CartItemRecord::from).toList();
        int total = items.stream().mapToInt(item -> item.price() * item.quantity()).sum();
        return new CartRecord(cart.getAppUser().getUsername(), items, total);
    }
}
