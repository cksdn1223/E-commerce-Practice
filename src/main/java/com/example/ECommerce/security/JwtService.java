package com.example.ECommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    // 토큰 만료 시간 (24시간)
    static final long EXPIRATION = 86400000;
    // 'Bearer ' 접두사
    static final String PREFIX = "Bearer";
    // HS256 알고리즘을 사용하는 서명 키. 애플리케이션 실행 시마다 새로운 키가 생성됩니다.
    // 실제 운영 환경에서는 이 키를 외부 설정 파일(e.g., application.yml)에서 관리해야 합니다.
    static final Key key = Jwts.SIG.HS256.key().build();

    /**
     * 사용자 이름을 받아 JWT 토큰을 생성합니다.
     *
     * @param username 사용자 이름
     * @return 생성된 JWT 문자열
     */
    public String getToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    /**
     * HTTP 요청의 Authorization 헤더에서 토큰을 추출하고,
     * 토큰을 검증하여 사용자 이름을 반환합니다.
     * @param request HttpServletRequest 객체
     * @return 추출된 사용자 이름, 유효하지 않으면 null
     */
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            // 토큰 파싱 및 서명 검증
            return Jwts.parser()
                    .verifyWith((SecretKey) key) // 서명 검증을 위해 동일한 키를 사용
                    .build()
                    .parseSignedClaims(token.replace(PREFIX, "").trim()) // 'Bearer ' 접두사와 앞뒤 공백 제거
                    .getPayload()
                    .getSubject();
        }
        return null;
    }
}
