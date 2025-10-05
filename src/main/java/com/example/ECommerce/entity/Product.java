package com.example.ECommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String name; // 상품이름

    @Getter
    @Setter
    private int price;  // 가격

    @Getter
    @Setter
    private int stockQuantity; // 재고 수량

    public Product(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //== 비즈니스 로직 추가 ==//

    /**
     * 재고 수량을 증가시키는 메소드 (주문 취소 등)
     * @param quantity 증가할 수량
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 수량을 감소시키는 메소드 (주문 시 사용)
     * @param quantity 감소할 수량
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다. (Not enough stock)");
        }
        this.stockQuantity = restStock;
    }
}
