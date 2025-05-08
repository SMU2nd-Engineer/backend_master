package com.culturemoa.cultureMoaProject.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LogoutController
 * 로그 아웃을 실행하기 위한 컨트롤러
 */
@RestController
public class LogoutController {

    /**
     * logout 
     * 쿠키에 저장된 refresh 기한을 0으로 덮어써서 제거, locatStorage 제거하여 로그아웃
     * @param pRequest : 요청
     * @param pResponse : 응답
     * @return 200 응답을 전달
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        // 같은 쿠키를 덮어 쓰기 위하여 쿠키를 추가하는 로직을 그대로 사용하기
        // HttpOnly 방식으로 refreshToken 저장
        ResponseCookie cookie =
                ResponseCookie.from("refreshToken", "") // 로그아웃이니 값은 업도록 변경
                .httpOnly(true)   // JavaScript에서 접근 불가능하게 설정 (보안 강화)
                .secure(false)    // HTTPS 환경에서는 true로 설정 (개발 환경에서는 false 가능)
                .path("/")        // 모든 경로에서 쿠키 전송 (ex: /api/xxx 요청 시 자동 포함됨)
                .maxAge(0) // 쿠키 유효기간 0으로 하여 지우도록 만들기
                .sameSite("Strict") // CSRF 방지: 동일 출처의 요청에만 쿠키 전송
                .build();          // 위 설정을 바탕으로 쿠키 객체 생성
        // 생성한 쿠키를 응답 헤더에 추가 (브라우저에 저장 지시)
        pResponse.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok("Logout and delete refresh cookie");
    }
}
