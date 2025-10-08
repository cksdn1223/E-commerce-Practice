package com.example.ECommerce;

import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.entity.Product;
import com.example.ECommerce.repository.AppUserRepository;
import com.example.ECommerce.repository.OrderItemRepository;
import com.example.ECommerce.repository.OrderRepository;
import com.example.ECommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class ECommerceApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	private final AppUserRepository appUserRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;
	private final PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
		AppUser admin = appUserRepository.save(new AppUser("admin", passwordEncoder.encode("admin"), "ADMIN"));
		AppUser user = appUserRepository.save(new AppUser("user", passwordEncoder.encode("user"), "USER"));

		// 상품 추가
		Product banana = productRepository.save(new Product("바나나", 2000, 20));
		Product apple = productRepository.save(new Product("사과", 3000, 30));
		Product strawberry = productRepository.save(new Product("딸기", 5000, 40));
		Product kiwi = productRepository.save(new Product("키위", 6000, 50));


	}
}
