package com.example.ECommerce.controller;

import com.example.ECommerce.dto.Product.ProductRecord;
import com.example.ECommerce.dto.Product.ProductResponse;
import com.example.ECommerce.dto.Product.ProductUpdateRecord;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Product Management", description = "상품 관리 API")
public class ProductController {
    private final ProductService productService;

    @PostMapping("products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRecord productRecord) {
        Product saveProduct = productService.saveProduct(productRecord);

        // 현재 요청을 기반으로 생성된 URI를 헤더에 붙여주면 좋음
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // 현재 URL에 /{id}를 덧붙임
                .buildAndExpand(saveProduct.getId()) // {id} 자리에 id값을 채움
                .toUri(); // URI로 변환 ( /api/products/123 )
        return ResponseEntity.created(location).body(ProductResponse.from(saveProduct));
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductRecord> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping("products")
    public ResponseEntity<List<ProductRecord>> findProducts(@RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(productService.findProductByKeyword(keyword));
        }
        return ResponseEntity.ok(productService.findAll());
    }

    @PatchMapping("products/{id}")
    public ResponseEntity<ProductRecord> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRecord productUpdateRecord) {
        return ResponseEntity.ok(productService.updateProduct(id, productUpdateRecord));
    }

    @DeleteMapping("products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
