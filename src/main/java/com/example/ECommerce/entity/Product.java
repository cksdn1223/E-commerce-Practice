package com.example.ECommerce.entity;

import com.example.ECommerce.dto.Product.ProductUpdateRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name; // 상품이름

    private int price;  // 가격

    private int stockQuantity; // 재고 수량

    @Builder
    public Product(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //== 비즈니스 로직 추가 ==//
    /**
     * 상품 정보를 업데이트하는 비즈니스 메소드.
     * 개별 setter를 여는 대신, 의도가 명확한 단일 메소드를 제공합니다.
     */
    public void updateInfo(ProductUpdateRecord updateRecord) {
        if (updateRecord.name() != null) this.name = updateRecord.name();
        if (updateRecord.price() != null) this.price = updateRecord.price();
        if (updateRecord.stockQuantity() != null) this.stockQuantity = updateRecord.stockQuantity();
    }
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
