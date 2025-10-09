package com.example.ECommerce.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 메소드의 파라미터에 사용하면,
 * 현재 로그인한 사용자의 AppUser 객체를 주입해주는 커스텀 어노테이션.
 * @see com.example.ECommerce.security.resolver.LoginUserArgumentResolver
 */
@Target(ElementType.PARAMETER) // 이 어노테이션이 파라미터에만 사용될 수 있도록 지정
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 어노테이션 정보가 유지되도록 함
public @interface LoginUser {
}
