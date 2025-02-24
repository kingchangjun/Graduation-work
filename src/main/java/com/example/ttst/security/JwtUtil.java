package com.example.ttst.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your256bitsecretyour256bitsecret"; //32자 이상 문자열 사용
    private static final long EXPIRATION_TIME = 86400000; //24시간

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    //토큰 생성
    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
    //jwt 토큰에서 이메일 추출
    public String extractEmail(String token){
        try{
        return Jwts.parser()
                .verifyWith(key)  // SecretKey 타입 적용
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
            } catch (ExpiredJwtException e){
            throw new RuntimeException("JWT 토큰 만료");
        }catch (JwtException e){
            throw new RuntimeException("JWT 토큰이 유효x");
        }
    }

    //jwt 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
