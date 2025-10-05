package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)  // N:1
    @JoinColumn(name = "app_user_id")   // 외래키 컬럼명 지정
    private AppUser appUser;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order(AppUser appUser, List<OrderItem> orderItems){
        this.appUser = appUser;
        this.orderItems = orderItems;
    }
}
