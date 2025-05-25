package com.culturemoa.cultureMoaProject.common.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class AuthJwtService {

    @Autowired
    private JwtProvider jwtProvider;

    /**
     * tokenCreateSave
     * refreshToken은 쿠키에 저장하고, accessToken은 JwtDTO로 반환
     * @param pResponse : 응답헤더
     * @param pUserId : 토큰 생성 userId
     * @return JWT DTO를 활용하여 accessToken 전달할 수 있게 반환
     */
    public JwtDTO tokenCreateSave (HttpServletResponse pResponse, String pUserId, Boolean autoLogin) {
    String accessToken  = jwtProvider.generateAccessToken(pUserId);
    String refreshToken = jwtProvider.generateRefreshToken(pUserId);
    // HttpOnly 방식으로 refreshToken 저장
    // 로그인 성공 후 Refresh Token을 HttpOnly 쿠키에 저장하는 로직
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from("refreshToken", refreshToken) // 쿠키 이름과 값 지정
            .httpOnly(true)   // JavaScript에서 접근 불가능하게 설정 (보안 강화)
            .secure(false)    // HTTPS 환경에서는 true로 설정 (개발 환경에서는 false 가능)
            .path("/")        // 모든 경로에서 쿠키 전송 (ex: /api/xxx 요청 시 자동 포함됨)
            .sameSite("Strict");

        // 자동 로그인일 경우 쿠키의 기간을 늘림.
//        if(autoLogin) {// CSRF 방지: 동일 출처의 요청에만 쿠키 전송
        if (Boolean.TRUE.equals(autoLogin)) { // null 방어를 위하여 추가함.
            cookieBuilder.maxAge(60 * 60 * 24 * 7);  }// 쿠키 유효기간 7일 (초 단위)

    // 위 설정을 바탕으로 쿠키 객체 생성 및 쿠키를 응답 헤더에 추가 (브라우저에 저장 지시)
    pResponse.addHeader("Set-Cookie", cookieBuilder.build().toString());

    return new JwtDTO(accessToken);

    }

}
