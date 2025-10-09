package com.example.ECommerce.repository;

import com.example.ECommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Containing  키워드 사용하면 LIKE %keyword% 쿼리 처럼 작동함
    List<Product> findByNameContaining(String keyword);
}
