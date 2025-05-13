package com.culturemoa.cultureMoaProject.user.exception;

import lombok.Getter;

/**
 * 소셜 로그인 검색이 안되었을 때 회원가입 유도시 가져갈 데이터를 담을 예외 클래스
 * SOCIAL_ID : 소셜 id
 * PROVIDER : 소셜 로그인 진행한 곳
 */
@Getter
public class SocialUserNotFoundException extends RuntimeException {

    private final String SOCIAL_ID;
    private final String PROVIDER;

    // 생성자에 getter 방식을 적용하여 값을 주입
    public SocialUserNotFoundException(String socialId, String provider ) {
      super("소셜 로그인 사용자가 없습니다.");
      this.SOCIAL_ID = socialId;
      this.PROVIDER = provider;
    }

}
