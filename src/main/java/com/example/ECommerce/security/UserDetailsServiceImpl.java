package com.example.ECommerce.security;

import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Spring Security의 UserDetailsService 인터페이스 구현체입니다.
 * 사용자 이름(username)을 기반으로 데이터베이스에서 사용자 정보를 조회하고,
 * Spring Security가 이해할 수 있는 UserDetails 객체로 변환하는 역할을 합니다.
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    /**
     * 사용자 이름으로 사용자 정보를 로드합니다.
     * AuthenticationFilter에서 인증 객체를 생성할 때 호출됩니다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }
}
