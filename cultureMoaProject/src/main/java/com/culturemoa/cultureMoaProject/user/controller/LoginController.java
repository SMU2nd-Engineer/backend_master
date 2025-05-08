package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.AuthJwtService;
import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.common.jwt.JwtValidator;
import com.culturemoa.cultureMoaProject.user.dto.UserDTO;
import com.culturemoa.cultureMoaProject.user.service.IdPasswordMatchService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
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
            System.out.println("로그인 기능을 시작함" );
            String userId = idPasswordMatchService.login(pRequest.getId(), pRequest.getPassword());
            System.out.println("로그인 결과 userId: " + userId);
            // accessToken / refreshToken 생성
            if(!userId.isEmpty()) {
                System.out.println("/user/login에서 userId 받아서 실행됨");
                JwtDTO jwtDTO = authJwtService.tokenCreateSave(pResponse, userId);
                System.out.println("/user/login에서 userId 받아서 실행됨" + jwtDTO);
                return ResponseEntity.ok(jwtDTO);
            } else {
                System.out.println("오류발생");
                return ResponseEntity.status(409).body("Invalid credentials");
            }
        } catch (Exception e) {
            log.error("e: 에러가 발생함"); // 예외 로그 출력 추가
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }
    }

}
