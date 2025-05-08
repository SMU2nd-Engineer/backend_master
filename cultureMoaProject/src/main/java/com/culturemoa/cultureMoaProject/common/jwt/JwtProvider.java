package com.culturemoa.cultureMoaProject.common.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 *  JwtProvider
 *  JWT 토큰 2가지(Access, Refresh 생성 및 토큰 검증
 */

@Component
public class JwtProvider {

    // 키 생성 - 비밀키를 디코드해서 사용하기 위한 코드
    private final Key KEY;
    private static final long ACCESS_TOKEN_VALIDAITY = 1000 * 60 * 30; // accessToken 유효시간 30분
    private static final long REFRESH_TOKEN_VALIDAITY = 1000 * 60 * 60 * 24 * 7; // accessToken 유효시간 7일

    // key 값 주입하기
    public JwtProvider(@Value("${jwt.temp.secretkey}") String secretKey) {
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        this.KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * generateAccessToken
     * access jwt 토큰 생성
     * pUserId : 유저 아이디 정보
     */
    public String generateAccessToken (String pUserId) {
        // 토큰 발급하기
        // String token =
        return Jwts.builder()
                .setSubject("userId") // 제목
                .claim("userId", pUserId) // 유저 정보를 토큰에 담음
                .setIssuedAt(new Date()) // 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDAITY)) // 만료 시간 30분 잡음
                .signWith(KEY)
                .compact();
    }

    public String generateRefreshToken (String pUserId) {
        // 토큰 발급하기
        // String token =
        return Jwts.builder()
                .setSubject("userId") // 제목
                .claim("userId", pUserId) // 유저 정보를 토큰에 담음
                .setIssuedAt(new Date()) // 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDAITY)) // 만료 시간을 7일로 잡음
                .signWith(KEY)
                .compact();
    }


    /*
     * getUserIdFromToken
     * refresh 토큰으로 새로운 access 토큰 생성할 때 필요한 userId 추출
     */
    public String getUserIdFromToken (String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class);
    }
}
