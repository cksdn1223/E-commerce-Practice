package com.example.ECommerce;

import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.repository.AppUserRepository;
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
	private final PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
		appUserRepository.save(new AppUser("admin", passwordEncoder.encode("admin"), "ADMIN"));
	}
}
