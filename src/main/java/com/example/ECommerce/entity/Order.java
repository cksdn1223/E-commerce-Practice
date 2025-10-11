package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)  // N:1
    @JoinColumn(name = "app_user_id")   // 외래키 컬럼명 지정
    private AppUser appUser;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    //== 생성 시점 관련 ==//
    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
    }

    //== 연관관계 편의 메소드 ==//
    void setAppUser(AppUser appUser) {
        this.appUser = appUser;
        appUser.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //== 생성 메소드 (정적 팩토리 메소드) ==//
    public static Order createOrder(AppUser appUser, List<OrderItem> orderItems) {
        Order order = new Order();
        appUser.addOrder(order);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }
}
