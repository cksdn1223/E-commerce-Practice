package com.example.ECommerce.controller;

import com.example.ECommerce.dto.Product.ProductRecord;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("products/{id}")
    public ResponseEntity<ProductRecord> findProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductById(id),HttpStatus.OK);
    }

    @GetMapping("products")
    public ResponseEntity<List<ProductRecord>> findAllProduct() {
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
}
