package com.culturemoa.cultureMoaProject.user.exception;

/**
 * 유저 정보가 조회되지 않을 경우 예외
 */
public class DontMatchUserInfoException extends RuntimeException {
    public DontMatchUserInfoException() {
        super("입력한 정보를 다시 확인해주세요.");
    }
}
