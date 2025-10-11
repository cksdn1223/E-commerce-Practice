package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Cart(AppUser appUser, List<CartItem> cartItems) {
        this.appUser = appUser;
        this.cartItems = cartItems;
    }

    //== 생성 메소드 (정적 팩토리 메소드) ==//
    public static Cart createCart(AppUser appUser) {
        return Cart.builder().appUser(appUser).build();
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
