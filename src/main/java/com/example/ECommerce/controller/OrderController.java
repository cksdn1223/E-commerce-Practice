package com.example.ECommerce.controller;


import com.example.ECommerce.dto.Order.OrderRequest;
import com.example.ECommerce.dto.Order.OrderResponse;
import com.example.ECommerce.entity.Order;
import com.example.ECommerce.repository.AppUserRepository;
import com.example.ECommerce.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Order Management", description = "주문 관리 API")
public class OrderController {
    private final OrderService orderService;
    private final AppUserRepository appUserRepository;

    @PostMapping("orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal UserDetails userDetails) {
        Order createdOrder = orderService.createOrder(userDetails, orderRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();
        return ResponseEntity.created(location).body(OrderResponse.from(createdOrder));
    }

    @GetMapping("orders")
    public ResponseEntity<List<OrderResponse>> findAllOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.findAllOrders(userDetails.getUsername()));
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
