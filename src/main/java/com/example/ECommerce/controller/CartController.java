package com.example.ECommerce.controller;

import com.example.ECommerce.dto.Cart.CartRecord;
import com.example.ECommerce.dto.CartItem.CartItemRequest;
import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.entity.Cart;
import com.example.ECommerce.security.annotation.LoginUser;
import com.example.ECommerce.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Cart Management", description = "장바구니 관리 API")
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<Void> addProductToCart(@RequestBody CartItemRequest request, @LoginUser AppUser appUser) {
        cartService.addProductToCart(appUser.getUsername(), request.productId(), request.quantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<CartRecord> getCart(@LoginUser AppUser appUser) {
        return ResponseEntity.ok().body(cartService.getCart(appUser.getUsername()));
    }
}
