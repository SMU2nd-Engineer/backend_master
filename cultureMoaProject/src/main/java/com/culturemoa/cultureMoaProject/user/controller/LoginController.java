package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.common.jwt.JwtValidator;
import com.culturemoa.cultureMoaProject.user.dto.UserDTO;
import com.culturemoa.cultureMoaProject.user.service.IdPasswordMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private IdPasswordMatchService idPasswordMatchService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtValidator jwtValidator;

    @Autowired
    private AuthJwtService authJwtService;

    @PostMapping("/login")
    public ResponseEntity<?> loginAccess(@RequestBody UserDTO pRequest, HttpServletResponse pResponse) {
        try {
            // 아이디 비밀번호 검증 후 토큰을 반환
            String userId = idPasswordMatchService.login(pRequest.getUserId(), pRequest.getPassword());
            // accessToken / refreshToken 생성
            if(userId != null) {

                JwtDTO jwtDTO = authJwtService.tokenCreateSave(pResponse, userId);
                return ResponseEntity.ok(jwtDTO);
            } else {
                return ResponseEntity.status(409).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        //쿠키를 가져옴
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies ) {
            // 쿠키를 조회하여 refresh 만 가져옴
            if("refreshToken".equals(cookie.getName())){
                refreshToken = cookie.getValue();
                break;
            }
        }

        // refreshToken 검증 1 : 리프레시 토큰이 있는지 없는지 확인
        if (refreshToken == null) {
            return ResponseEntity.status(403).body("refreshToken is Empty");
        }

        if(!jwtValidator.validatorToken(refreshToken)) {
            return ResponseEntity.status(403).body("Expired_data");
        }

        String userId = jwtProvider.getUserIdFromToken(refreshToken);

        //새로 accessToken 발급 받기
        String newAccessToken = jwtProvider.generateAccessToken(userId);

        JwtDTO jwtDTO = new JwtDTO(newAccessToken);
        return ResponseEntity.ok(jwtDTO); // 리프레시로 새로 생성한 newAccessToken을 반환
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        // 같은 쿠키를 덮어 쓰기 위하여 쿠키를 추가하는 로직을 그대로 사용하기
        // HttpOnly 방식으로 refreshToken 저장
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "") // 로그아웃이니 값은 업도록 변경
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
