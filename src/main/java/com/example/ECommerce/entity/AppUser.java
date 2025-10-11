package com.example.ECommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String password;

    private String role;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // 1. 기본 'USER' 역할을 생성하는 빌더
    @Builder(builderMethodName = "builder")
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    // 2. 역할을 포함해 생성하는 빌더
    @Builder(builderMethodName = "roleBuilder")
    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void changePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

    /**
     * AppUser에 주문을 추가할 때, Order 객체에도 AppUser를 설정하여 양방향 관계를 한번에 설정합니다.
     */
    public void addOrder(Order order) {
        this.orders.add(order);
        order.setAppUser(this); // 이 코드가 동작하려면 Order 엔티티에 setAppUser가 필요합니다.
    }
}
