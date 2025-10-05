package com.example.ECommerce.service;

import com.example.ECommerce.dto.Product.ProductRecord;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductRecord saveProduct(ProductRecord productRecord) {
        Product product = new Product(productRecord.name(), productRecord.price(), productRecord.stockQuantity());
        productRepository.save(product);
        return new ProductRecord(product.getName(), product.getPrice(), product.getStockQuantity());
    }
}
