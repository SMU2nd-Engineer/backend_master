package com.culturemoa.cultureMoaProject.user.exception;

/**
 * 소셜 로그인 과정에서 토큰 을 받아오지 못 했을 때 커스텀 예외
 */
public class SocialNoAccessTokenException extends RuntimeException {
    public SocialNoAccessTokenException () {
        super("소셜 로그인에서 토큰을 받아오지 못 했습니다.");
    }
}
