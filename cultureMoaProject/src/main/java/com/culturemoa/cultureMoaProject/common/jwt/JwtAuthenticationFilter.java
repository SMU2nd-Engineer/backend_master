package com.culturemoa.cultureMoaProject.common.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthenticationFilter
 * 커스텀 필터로써 JWT 토큰을 검증하는 단계로 시큐리티 보다 먼저 진행.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtValidator jwtValidator;

    // JwtProvider는 토큰을 생성 및 검증하는 클래스, 생성자로 주입 받음
    public JwtAuthenticationFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    /**
     * 커스텀 필터 정의 메서드 JWT 토큰 을 확인하고 인증 성공하면 통과 없으면 오류를 반환
     * @param pRequest : 요청 변수
     * @param pResponse : 응답 변수
     * @param pFilterChain : 필터체인 변수
     * @throws ServletException : 필터 체인 관련 오류 처리
     * @throws IOException : 필터 체인 관련 오류 처리
     */
    @Override
    // 상위에서 지정한 어노테이션인 @NonNull을 추가가
    protected void doFilterInternal(@NonNull HttpServletRequest pRequest, @NonNull HttpServletResponse pResponse, @NonNull FilterChain pFilterChain)
            throws ServletException, IOException {
        // 토큰 검증 예외 처리(로그인, 회원가입, 아이디/비밀번호 찾기 등 토큰 발급이 필요 없는 경우 추가하기 위해서 넣음)
        // 디버깅 코드
        System.out.println("[Filter] JwtAuthenticationFilter 실행됨");
        System.out.println("[Filter] 요청 URI: " + pRequest.getRequestURI());

        String requestURI = pRequest.getRequestURI();

        // 토큰 발행을 안 해도 요청을 선택함.
        if (
                requestURI.equals("/login") || // 로그인 토큰이 없는 단계
                requestURI.equals("/refresh") || // 리프레시 또한 토큰이 없는 단계
                requestURI.equals("/kakaoAuth")|| // 소셜 로그인 토큰이 없는 단계
                requestURI.equals("/naverAuth") || // 소셜 로그인 토큰이 없는 단계
                requestURI.equals("/googleAuth") ||// 소셜 로그인 토큰이 없는 단계
                requestURI.equals("/logout") ||// refresh 만료 후 로그인 페이지 돌아 갈 때 남아 있는 토큰 제거하기 위해 필요
                requestURI.equals("/duplicatecheck") ||
                requestURI.equals("/registration")

                ) {
            System.out.println("[Filter] 예외 경로 요청 - 필터 패스: " + requestURI);
            pFilterChain.doFilter(pRequest, pResponse);
            return;
        }
        System.out.println(" return 이 걸리지 않고서 여기가 실행됨");
        String token = resolveToken(pRequest);
        System.out.println(token);
        if (token == null) { // 토큰이 비어있을 경우 401 반환
            pResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            pResponse.setContentType("application/json"); // axios에서 정확히 받도록
            pResponse.setCharacterEncoding("UTF-8"); // 한글 깨지지 않도록 설정
            pResponse.getWriter().write("{\"error\": \"토큰이 없습니다.\"}");
            return; // 요청 중단
        }
        try {
            // 검증 진행 후 검증이 true 이면 시큐리티 인증 되도록 authentication 변수 생성
            if( jwtValidator.validatorToken(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        // 추가 적인 권한을 설정 할려면 인자를 null이 아닌 권한으로 설정할 것.
                        new UsernamePasswordAuthenticationToken(token, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                pFilterChain.doFilter(pRequest, pResponse); // 다음 단계로 가도록 넣어준 코드
                return;
            }

        } catch (RuntimeException e) {
            // 토큰이 유효하지 않으니 401 error 반환하도록 구성
            pResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            pResponse.setContentType("application/json"); // axios에서 정확히 받도록
            pResponse.setCharacterEncoding("UTF-8"); // 한글 깨지지 않도록 설정
            pResponse.getWriter().write("{\"error\": \"유효하지 않은 토큰입니다.\"}");
            return; // 요청 중단
        }
//        filterChain.doFilter(request, response); // 다음 필터로 전달 - 스프링 시큐리티 - 잘 못 되코드로 일단 주석 처리
    }

    /**
     * resolveToken
     * 요청 헤더에서 "Authorization" 값을 가져와서 'Bearer ' 이후 토큰만 추출
     * @param request : 요청 값 담은 변수
     * @return : 헤더에서 토큰을 반환
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 토큰 값 반환
        }
        return null;
    }

}
