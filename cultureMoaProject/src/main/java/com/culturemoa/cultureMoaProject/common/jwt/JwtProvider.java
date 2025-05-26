package com.culturemoa.cultureMoaProject.common.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

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

    /**
     * 토큰을 사용자 정보로 변환하는 메서드
     * @param accessToken 엑세스토큰
     * @return 복호화된 UserId
     */
    public String getUserInfoByToken(String accessToken){
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(accessToken).getBody();

            if(claims.isEmpty()) return "";

            return claims.get("userId").toString();
        } catch (ExpiredJwtException e) {
            return e.getClaims().toString();
        }
    }

    /**
     * resolveToken
     * 요청 헤더에서 "Authorization" 값을 가져와서 'Bearer ' 이후 토큰만 추출
     * @param request : 요청 값 담은 변수
     * @return : 헤더에서 토큰을 반환
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 토큰 값 반환
        }
        return null;
    }

}
