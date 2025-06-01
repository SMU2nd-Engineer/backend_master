package com.culturemoa.cultureMoaProject.chat;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {

            HttpServletRequest req = servletRequest.getServletRequest();
            String token = req.getParameter("token");
            request.getHeaders().setBearerAuth(token);
        }
        System.out.println("Handshake Headers = "+request.getHeaders());

        // Origin 체크 예시
        String origin = request.getHeaders().getOrigin();
        if (origin != null && (origin.equals("http://3.38.104.183") || origin.equals("https://ec2-3-38-104-183.ap-northeast-2.compute.amazonaws.com"))) {
            return true;
        }
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
