package com.culturemoa.cultureMoaProject.common.security;

import com.culturemoa.cultureMoaProject.common.jwt.JwtAuthenticationFilter;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.common.jwt.JwtValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

/**
 * SecurityConfig
 * spring security 커스텀 설정
 * jwtValidator : JWT 검증을 위한 생성자 DI 필드
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${cors.auth.path}")
    private String frontPath;



    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;

    // JwtProvider는 토큰을 생성 및 검증하는 클래스, 생성자로 주입 받음
    public SecurityConfig(JwtValidator jwtValidator, JwtProvider jwtProvider) {
        this.jwtValidator = jwtValidator;
        this.jwtProvider = jwtProvider;
    }

    /**
     * securityFilterChain
     * 스프링 시큐리티 커스텀 필터 적용
     * http: 시큐리티 필터체인을 전달 받을 매개 변수
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (API 서버에서는 보통 꺼둠)
            .cors(cors -> {})             // CORS 설정 적용 (아래 Bean에서 지정)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/user/login",
                                     "/refresh",
                                     "/user/logout",
                                     "/user/kakaoAuth",
                                     "/user/naverAuth",
                                     "/user/googleAuth",
                                     "/user/duplicatecheck",
                                     "/user/idFind",
                                     "/user/passwordFind",
                                     "/user/passwordChange",
                                     "/logout"
                                     ).permitAll() // 인증을 자동 허용하는 경로들
                    .requestMatchers("/ws/**").permitAll() // 웹소켓 연결 허용하는 코드
//                    .requestMatchers("/**").permitAll() // 모든 경로를 허용하는 test 코드
                    .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
            )
            .logout(logout -> logout.disable()) // 시큐리티 기본 로그아웃으로 get 요청을 방지
            .addFilterBefore(new JwtAuthenticationFilter(jwtValidator, jwtProvider), UsernamePasswordAuthenticationFilter.class); // JWT 필터 등록 (기존 필터 앞에 추가)
        return http.build();
    }

    /**
     * corsConfigurationSource
     * corf 보안 정책를 회피하기 위하여 전역으로 설정하기 위한 메서드
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontPath)); // 리액트 포트 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 허용 여부

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

