package com.culturemoa.cultureMoaProject.common.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final JwtProvider jwtProvider;
    private final String apiPrefix;

    // JwtProvider는 토큰을 생성 및 검증하는 클래스, 생성자로 주입 받음
    public JwtAuthenticationFilter(JwtValidator jwtValidator, JwtProvider jwtProvider, String apiPrefix) {
        this.jwtProvider = jwtProvider;
        this.jwtValidator = jwtValidator;
        this.apiPrefix = apiPrefix;
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
        // 디버깅 코드 - 나중에 지울것
        System.out.println("[Filter] JwtAuthenticationFilter 실행됨");
        System.out.println("[Filter] 요청 URI: " + pRequest.getRequestURI());
        String requestURI = pRequest.getRequestURI();


        List<String> permitPaths = PrefixFilterPassPaths.getPermitPaths(apiPrefix);
        // 모든 경로를 jwt 토큰 검증을 빼기 위하여 설정 나중에 필요한 항목만 넣어주어야 함.
        if (permitPaths.contains(requestURI)
                ) {
            System.out.println("[Filter] 예외 경로 요청 - 필터 패스: " + requestURI);
            // 검증을 건너 뛰어도 문제가 생기지 않게 하기 위해서 인증 객체를 임의 생성
            UsernamePasswordAuthenticationToken authentication =
                    // 추가 적인 권한을 설정 할려면 인자를 null이 아닌 권한으로 설정할 것.
                    new UsernamePasswordAuthenticationToken("JwtValidPass", null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            pFilterChain.doFilter(pRequest, pResponse);
            return;
        }
        String token = jwtProvider.resolveToken(pRequest); // 헤더에서 토큰만 추출
        if (token == null) { // 토큰이 비어있을 경우 401 반환
            sendErrorResponse(pResponse, "토큰이 없습니다.");
            return; // 요청 중단
        }
        try {
            if( !jwtValidator.validatorToken(token)) {
                sendErrorResponse(pResponse, "유효하지 않은 토큰입니다.");
                return; // 요청 중단
            }
            // 인증 객체에 담을 정보 추출 - 아이디 및 권한 설정을 위한 정보
            String userId = jwtProvider.getUserIdFromToken(token); // 아이디 추출
            // 권한 설정
            String role = userId.equals("admin123") ? "ROLE_ADMIN" : "ROLE_USER"; //  관례를 지켜서 권한 설정
            // GrantedAuthority 구현한 클래스인 SimpleGrantedAuthority 사용 하나 확장성을 위해 List로 설정
            List<GrantedAuthority> authorityList =
                    // 인텔리제이 추천 및 스프링 추천 방식인 불변 리스트를 만드는 Java 유틸리티 메서드 사용
                    Collections.singletonList(new SimpleGrantedAuthority(role)); // 인텔리제이 추천 및 스프링 추천인

            // 검증 진행 후 검증이 true 이면 시큐리티 인증 되도록 authentication 변수 생성
                UsernamePasswordAuthenticationToken authentication =
                        // 추가 적인 권한을 설정 할려면 인자를 null이 아닌 권한으로 설정할 것.
                        new UsernamePasswordAuthenticationToken(userId, null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                pFilterChain.doFilter(pRequest, pResponse); // 다음 단계로 가도록 넣어준 코드
                return;

        } catch (RuntimeException e) {
            // 토큰이 유효하지 않으니 401 error 반환하도록 구성
            sendErrorResponse(pResponse, "유효하지 않은 코드입니다.");
            return; // 요청 중단
        }
    }

    private void sendErrorResponse(HttpServletResponse pResponse, String message) throws IOException {
        pResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        pResponse.setContentType("application/json"); // axios에서 정확히 받도록
        pResponse.setCharacterEncoding("UTF-8"); // 한글 깨지지 않도록 설정
        pResponse.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}
