package com.example.ECommerce.service;

import com.example.ECommerce.dto.Order.OrderResponse;
import com.example.ECommerce.dto.OrderItem.OrderItemRequest;
import com.example.ECommerce.dto.Order.OrderRequest;
import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.entity.Order;
import com.example.ECommerce.entity.OrderItem;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.exception.ResourceNotFoundException;
import com.example.ECommerce.repository.AppUserRepository;
import com.example.ECommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AppUserRepository appUserRepository;
    private final ProductService productService;
    //*************************
    // 로직 구현 구역
    //*************************

    @Transactional
    public Order createOrder(UserDetails userDetails, OrderRequest orderRequest) {
        // 사용자 조회
        AppUser orderUser = appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()->new ResourceNotFoundException(userDetails.getUsername()+" 를 가진 유저를 찾을 수 없음"));
        // 주문 상품 목록 생성
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : orderRequest.orderItems()){
            Product product = productService.findProduct(itemRequest.productId());
            OrderItem orderItem = OrderItem.createOrderItem(product, product.getPrice(), itemRequest.quantity());
            orderItems.add(orderItem);
        }
        // Order 생성 책임을 Order 클래스에 위임 (어려움) ************************
        // 주문 생성 및 저장
        Order order = Order.createOrder(orderUser, orderItems);
        return orderRepository.save(order);
    }

    // find
    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("ID "+orderId +" 를 가진 주문을 찾을 수 없음"));
        return OrderResponse.from(order);
    }

    // delete
    @Transactional
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
