package com.culturemoa.cultureMoaProject.common.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtValidator jwtValidator;

    // JwtProvider는 토큰을 생성 및 검증하는 클래스, 생성자로 주입 받음
    public JwtAuthenticationFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    // 상위에서 지정한 어노테이션인 @NonNull을 추가가
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 검증 예외 처리
        // 디버깅 코드
        System.out.println("[Filter] JwtAuthenticationFilter 실행됨");
        System.out.println("[Filter] 요청 URI: " + request.getRequestURI());
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/*")
//        requestURI.equals("/login")
//                ||
//                requestURI.equals("/refresh") ||
//                requestURI.equals("/logout")||
//                requestURI.equals("/kakaoAuth")||
//                requestURI.equals("/naverAuth") ||
//                requestURI.equals("/googleAuth")
                ) {
            System.out.println("[Filter] 예외 경로 요청 - 필터 패스: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(" return 이 걸리지 않고서 여기가 실행됨");
        String token = resolveToken(request);
        System.out.println(token);
        if (token != null) { // 토큰이 비어있지 않으면 실행
            try {
                // 검증 진행 -
                if( jwtValidator.validatorToken(token)) {
                    filterChain.doFilter(request, response); // 다음단계로 넘어가도록 설정
                    return;
                }
                // 추가적인 인증 객체 생성 및 인가를 구현할려면 아래에 코드를 작성할 것
            } catch (RuntimeException e) {
                // 토큰이 유효하지 않으니 401 error 반환하도록 구성
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.setContentType("application/json"); // axios에서 정확히 받도록
                response.setCharacterEncoding("UTF-8"); // 한글 깨지지 않도록 설정
                response.getWriter().write("{\"error\": \"유효하지 않은 토큰입니다.\"}");
                return; // 요청 중단
            }

        }
        filterChain.doFilter(request, response); // 다음 필터로 전달 - 스프링 시큐리티
    }

    // 요청 헤더에서 "Authorization" 값을 가져와서 Bearer 토큰만 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 토큰 값 반환
        }
        return null;
    }

}
