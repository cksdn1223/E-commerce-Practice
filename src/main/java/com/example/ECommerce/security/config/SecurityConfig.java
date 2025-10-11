package com.example.ECommerce.security.config;

import com.example.ECommerce.security.AuthEntryPoint;
import com.example.ECommerce.security.AuthenticationFilter;
import com.example.ECommerce.security.UserDetailsServiceImpl;
import com.example.ECommerce.security.resolver.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // @PreAuthorize, @PostAuthorize, @Secured 등을 활성화합니다.
public class SecurityConfig implements WebMvcConfigurer {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPoint exceptionHandler;
    private final AuthenticationFilter authenticationFilter;
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    // 패스워드 암호화 시켜주는 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 인증 빈
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }
    // 커스텀 Argument Resolver 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (Stateless API 서버이므로)
                .cors(Customizer.withDefaults()) // CORS 설정 적용
                .sessionManagement(sess -> sess.sessionCreationPolicy( // 세션을 사용하지 않는 Stateless 정책 설정
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(auth -> auth // HTTP 요청에 대한 인가 규칙 설정
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // 로그인 API는 모두 허용
                        .requestMatchers(HttpMethod.POST, "/api/users/save").permitAll() // 회원가입 API는 모두 허용
                        // Swagger UI와 API 문서를 위한 경로를 인증 없이 허용합니다.
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터를 기본 로그인 필터 앞에 추가
                .exceptionHandling(ex -> ex.authenticationEntryPoint(exceptionHandler)) // 인증 예외 발생 시 처리할 핸들러 등록
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));

        config.setAllowCredentials(false);
//        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**",config);
        return source;
    }
}