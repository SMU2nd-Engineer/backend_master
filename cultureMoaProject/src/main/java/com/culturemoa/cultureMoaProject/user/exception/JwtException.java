package com.culturemoa.cultureMoaProject.user.exception;

public class JwtException extends RuntimeException {
    public JwtException() {
        super("토큰이 만료되었습니다.");
    }
}
