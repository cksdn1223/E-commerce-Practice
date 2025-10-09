package com.example.ECommerce.service;

import com.example.ECommerce.dto.Cart.CartRecord;
import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.entity.Cart;
import com.example.ECommerce.entity.CartItem;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.exception.ResourceNotFoundException;
import com.example.ECommerce.repository.AppUserRepository;
import com.example.ECommerce.repository.CartRepository;
import com.example.ECommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addProductToCart(String username, Long productId, int quantity) {
        // 사용자 장바구니 조회 후 없으면 생성
        Cart findCart = cartRepository.findByAppUser_Username(username)
                .orElseGet(()-> {
                    AppUser appUser = appUserRepository.findByUsername(username)
                            .orElseThrow(()-> new UsernameNotFoundException(username+" 을 가진 유저를 찾을 수 없습니다."));
                    Cart newCart = Cart.createCart(appUser);
                    return cartRepository.save(newCart);
                });
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("ID "+productId+" 에 해당하는 상품이 없습니다."));
        // cart에 상품 추가
        findCart.addProduct(product, quantity);
        // transactional 덕분에 cart save 불필요
    }

    @Transactional(readOnly = true)
    public CartRecord getCart(String username) {
        Cart cart = cartRepository.findByAppUser_Username(username)
                .orElseGet(() -> {
                    AppUser appUser = appUserRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException(username + " 을 가진 유저를 찾을 수 없습니다."));
                    return Cart.createCart(appUser); // DB에 저장하지 않고, 비어있는 Cart 객체만 생성
                });
        return CartRecord.from(cart);
    }
}
