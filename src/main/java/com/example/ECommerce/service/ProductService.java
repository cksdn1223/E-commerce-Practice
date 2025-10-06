package com.example.ECommerce.service;

import com.example.ECommerce.dto.Product.ProductRecord;
import com.example.ECommerce.dto.Product.ProductUpdateRecord;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.exception.ResourceNotFoundException;
import com.example.ECommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ID "+id+" 를 가진 상품을 찾을 수 없습니다."));
    }

    //*************************
    // API 구현 구역
    //*************************
    public Product saveProduct(ProductRecord productRecord) {
        Product saveProduct = new Product(productRecord.name(), productRecord.price(), productRecord.stockQuantity());
        return productRepository.save(saveProduct);
    }

    public ProductRecord findProductById(Long id) {
        Product findProduct = findProduct(id);
        return new ProductRecord(findProduct.getName(), findProduct.getPrice(), findProduct.getStockQuantity());
    }

    public List<ProductRecord> findAll() {
        List<Product> findProduct = productRepository.findAll();
        return findProduct.stream()
                .map(product ->
                new ProductRecord(
                        product.getName(),
                        product.getPrice(),
                        product.getStockQuantity()))
                .toList();
    }

    // 이름변경 가격변경 재고수량변경
    @Transactional
    public void updateProduct(Long id, ProductUpdateRecord productUpdateRecord) {
        Product findProduct = findProduct(id);
        if (productUpdateRecord.name() != null) findProduct.setName(productUpdateRecord.name());
        if (productUpdateRecord.price() != null) findProduct.setPrice(productUpdateRecord.price());
        if (productUpdateRecord.stockQuantity() != null) findProduct.setStockQuantity(productUpdateRecord.stockQuantity());
        // save 없어도 Transactional 덕분에 DB에 반영됨
    }

    // Delete
    @Transactional
    public void deleteProduct(Long id) {
        Product findProduct = findProduct(id);
        productRepository.delete(findProduct);
    }
}
