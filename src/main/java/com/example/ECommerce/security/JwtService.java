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
    static final long EXPIRATION = 86400000;
    static final String PREFIX = "Bearer";
//    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    static final Key key = Jwts.SIG.HS256.key().build();
    public String getToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token != null) {
            // 토큰 파싱 및 검증 (최신 API 사용)
            String user = Jwts.parser()
                    .verifyWith((SecretKey) key) // 서명 검증 키 설정
                    .build()
                    .parseSignedClaims(token.replace(PREFIX, "").trim()) // Bearer 접두사 및 공백 제거
                    .getPayload()
                    .getSubject();
            if(user != null) return user;
        }
        return null;
    }
}
