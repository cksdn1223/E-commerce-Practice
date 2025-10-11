package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;   //주문 수량
    private int orderPrice; // 주문 당시 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(Product product, int orderPrice, int quantity) {
        this.product = product;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    void setOrder(Order order) {
        this.order = order;
    }
    public static OrderItem createOrderItem(Product product, int orderPrice, int quantity) {
        product.removeStock(quantity); // 생성 시점에 재고 감소
        return OrderItem.builder()
                .product(product)
                .orderPrice(orderPrice)
                .quantity(quantity).build();
    }
}
