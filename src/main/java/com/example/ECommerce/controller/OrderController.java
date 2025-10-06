package com.example.ECommerce.controller;

import com.example.ECommerce.dto.Order.OrderRequest;
import com.example.ECommerce.dto.Order.OrderResponse;
import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.entity.Order;
import com.example.ECommerce.repository.AppUserRepository;
import com.example.ECommerce.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Order Management", description = "주문 관리 API")
public class OrderController {
    private final OrderService orderService;
    private final AppUserRepository appUserRepository;

    @PostMapping("orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser appUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException(userDetails.getUsername() + " 을 찾을 수 없습니다."));
        Order createdOrder = orderService.createOrder(appUser.getId(), orderRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();
        return ResponseEntity.created(location).body(OrderResponse.from(createdOrder));
    }
}
