package com.culturemoa.cultureMoaProject.common.jwt;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 필터 보다 먼저 실행하여 브라우저 orign을 찍는 클래스와 메서드
 */
public class CorsLoggingFilter implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            String origin = httpRequest.getHeader("Origin");
            String method = httpRequest.getMethod();
            System.out.println("[CORS 진입] Method: " + method + ", Origin: " + origin);
        }
        chain.doFilter(request, response);
    }
}
