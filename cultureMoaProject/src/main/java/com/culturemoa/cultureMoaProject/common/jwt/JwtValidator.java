package com.culturemoa.cultureMoaProject.common.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtValidator {

    private final Key KEY;

    // key 값 주입하기
    public JwtValidator(@Value("${jwt.temp.secretkey}") String secretKey) {
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        this.KEY = Keys.hmacShaKeyFor(keyBytes);
    }
    // 단순 유효성 검사이므로 boolen으로 처리하자
    public boolean validatorToken (String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다.");
            throw new RuntimeException("Expired JWT Token");
        } catch (Exception e) {
            System.out.println("토큰 검증 중 예외 발생");
            return false;
        }
    }
}
