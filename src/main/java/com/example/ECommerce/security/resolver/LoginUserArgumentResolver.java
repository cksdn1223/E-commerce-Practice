package com.example.ECommerce.security.resolver;

import com.example.ECommerce.entity.AppUser;
import com.example.ECommerce.repository.AppUserRepository;
import com.example.ECommerce.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AppUserRepository appUserRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @LoginUser 어노테이션이 붙어 있고, 파라미터 타입이 AppUser인지 확인
        return parameter.hasParameterAnnotation(LoginUser.class)
                && parameter.getParameterType().equals(AppUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 현재 인증된 사용자의 정보를 가져옴
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // username으로 DB에서 AppUser 엔티티를 찾아 반환
            return appUserRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
        }

        return null; // 인증 정보가 없으면 null 반환
    }
}