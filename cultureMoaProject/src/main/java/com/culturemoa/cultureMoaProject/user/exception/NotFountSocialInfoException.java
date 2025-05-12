package com.culturemoa.cultureMoaProject.user.exception;

/**
 * 소셜 로그인으로 토큰은 받았으나 사용자 정보를 받아오지 못 했을 때 오류
 */
public class NotFountSocialInfoException extends RuntimeException {
    public NotFountSocialInfoException () {
        super("토큰으로 사용자 정보를 가져오지 못 했습니다.");
    }
}
