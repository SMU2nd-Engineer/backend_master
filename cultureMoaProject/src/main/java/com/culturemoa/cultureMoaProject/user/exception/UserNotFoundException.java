package com.culturemoa.cultureMoaProject.user.exception;

/**
 * 유저 정보가 매칭되지 않을 때 정보 예외
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("사용자 정보가 없습니다.");
    }
}
