package com.example.ECommerce.repository;

import com.example.ECommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAppUser_Username(String username);
}
