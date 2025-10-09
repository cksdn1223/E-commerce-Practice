package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // N:1
    @JoinColumn(name = "cart_id")   // 외래키 컬럼명 지정
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    //== 생성 메소드 (정적 팩토리 메소드) ==//
    public static CartItem createCartItem(Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    //== 비즈니스 로직 ==//
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
    public void removeQuantity(int quantity) {
        this.quantity = Math.max(this.quantity - quantity, 0);
    }

}
