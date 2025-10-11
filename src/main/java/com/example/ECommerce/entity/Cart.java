package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    //== 생성 메소드 (정적 팩토리 메소드) ==//
    public static Cart createCart(AppUser appUser) {
        Cart cart = new Cart();
        cart.setAppUser(appUser);
        return cart;
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public void addProduct(Product product, int quantity) {
        CartItem existingItem = this.cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            existingItem.addQuantity(quantity);
        } else {
            CartItem newCartItem = CartItem.createCartItem(product, quantity);
            this.addCartItem(newCartItem);
        }
    }
}
