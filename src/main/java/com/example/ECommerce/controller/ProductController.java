package com.example.ECommerce.controller;

import com.example.ECommerce.dto.Product.ProductRecord;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Product Management", description = "상품 관리 API")
public class ProductController {
    private final ProductService productService;

    @PostMapping("products")
    public ResponseEntity<ProductRecord> addProduct(@RequestBody ProductRecord productRecord) {
        return new ResponseEntity<>(productService.saveProduct(productRecord), HttpStatus.CREATED);
    }
}
