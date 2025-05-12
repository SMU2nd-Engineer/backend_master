package com.culturemoa.cultureMoaProject.user.exception;

/**
 * 로그인 할 때 비밀번호가 일치하지 않을 때 예외
 */
public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
