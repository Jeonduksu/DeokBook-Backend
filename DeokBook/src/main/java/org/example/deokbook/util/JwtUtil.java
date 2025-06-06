package org.example.deokbook.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.example.deokbook.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    
    //액세트 토큰 만료시간
    private final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000;
    
    //리프레쉬 토큰 만료시간
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 14 * 24 * 60 * 60 * 1000;
    
    // 액세스 토큰 발급
    public String generateAccessToken(String email, Character rule) {
        return generateToken(email,rule,ACCESS_TOKEN_EXPIRATION_TIME);
    }

    // 리프레쉬 토큰 발급
    public String generateRefreshToken(String email, Character rule) {
        return generateToken(email,rule,REFRESH_TOKEN_EXPIRATION_TIME);
    }
    
    // 토큰 생성
    public String generateToken(String email, Character rule,long expirationTime) {
        Map<String,Object>claims = new HashMap<>();
        claims.put("rule",rule);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    // 이메일 추출
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // rule 추출
    public Character getRuleFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("rule",Character.class);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.info("잘못된 토큰 입니다 : {}", e.getMessage());
            return false;
        }
    }
}
