package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.common.jwt.JwtDTO;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.common.jwt.JwtValidator;
import com.culturemoa.cultureMoaProject.user.dto.LoginUserInfoDTO;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccessToken 만료 또는 없을 경우 Refresh 토큰으로 재발급하는 컨트롤러
 */
@RestController
public class TokenController {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final UserDAO userDAO;

    @Autowired
    public TokenController(JwtProvider jwtProvider, JwtValidator jwtValidator, UserDAO userDAO) {
        this.jwtProvider = jwtProvider;
        this.jwtValidator = jwtValidator;
        this.userDAO = userDAO;
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
            //없으면 401로 반환하여 로그아웃 유도
            return ResponseEntity.status(401).body("refreshToken is Empty");
        }

        if(!jwtValidator.validatorToken(refreshToken)) {
            //없으면 401로 반환하여 로그아웃 유도
            return ResponseEntity.status(401).body("Expired_data");
        }

        String userId = jwtProvider.getUserIdFromToken(refreshToken);

        //새로 accessToken 발급 받기
        String newAccessToken = jwtProvider.generateAccessToken(userId);

        LoginUserInfoDTO userInfo = userDAO.getUserInfoByUserId(userId);
        JwtDTO jwtDTO = new JwtDTO(newAccessToken, userInfo);
        return ResponseEntity.ok(jwtDTO); // 리프레시로 새로 생성한 newAccessToken을 반환
    }
}
