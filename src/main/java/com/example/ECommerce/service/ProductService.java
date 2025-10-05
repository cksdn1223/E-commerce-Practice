package com.example.ECommerce.service;

import com.example.ECommerce.dto.Product.ProductRecord;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.exception.ResourceNotFoundException;
import com.example.ECommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductRecord saveProduct(ProductRecord productRecord) {
        Product saveProduct = productRepository.save(new Product(productRecord.name(), productRecord.price(), productRecord.stockQuantity()));
        return new ProductRecord(saveProduct.getName(), saveProduct.getPrice(), saveProduct.getStockQuantity());
    }

    public ProductRecord findProductById(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ID "+id+" 를 가진 상품을 찾을 수 없습니다."));
        return new ProductRecord(findProduct.getName(), findProduct.getPrice(), findProduct.getStockQuantity());
    }

    public List<ProductRecord> findAll() {
        List<Product> findProduct = productRepository.findAll();
        if(findProduct.isEmpty()) throw new ResourceNotFoundException("상품이 존재하지 않습니다.");
        return findProduct.stream()
                .map(product ->
                new ProductRecord(
                        product.getName(),
                        product.getPrice(),
                        product.getStockQuantity()))
                .toList();
    }

    // 이름변경 가격변경 재고수량변경
}
