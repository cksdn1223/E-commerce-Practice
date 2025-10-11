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

/**
 * 커스텀 어노테이션인 @LoginUser를 처리하는 Argument Resolver 입니다.
 * 컨트롤러 메소드의 파라미터에 @LoginUser 어노테이션이 있으면,
 * 현재 인증된 사용자의 AppUser 엔티티를 찾아서 주입해주는 역할을 합니다.
 */
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AppUserRepository appUserRepository;

    /**
     * 이 Resolver가 특정 파라미터를 지원하는지 여부를 결정합니다.
     * &#064;LoginUser  어노테이션이 있고, 파라미터 타입이 AppUser 클래스일 때 true를 반환합니다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @LoginUser 어노테이션이 붙어 있고, 파라미터 타입이 AppUser인지 확인
        return parameter.hasParameterAnnotation(LoginUser.class)
                && parameter.getParameterType().equals(AppUser.class);
    }

    /**
     * supportsParameter가 true를 반환했을 때, 실제 파라미터에 주입할 객체를 반환하는 메소드입니다.
     * SecurityContext에서 인증 정보를 가져와 DB에서 AppUser를 조회한 후 반환합니다.
     */
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