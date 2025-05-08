package com.culturemoa.cultureMoaProject.user.dto;

import lombok.Data;

/**
 * KakaoUserInfoDTO
 * 카카오 토큰에서 사용자 정보를 역직렬화로 쉽게 꺼내기 위해 생성한 DTO
 * id : 회원번호 - userId로 사용할 값
 */
@Data
public class KakaoUserInfoDTO {
    private Long id;
    private KakaoAccount kakaoAccount;

    /**
     * kakaoAccount
     * json 객체 안에 json 객체 값을 가지고 오기 위한 내부 클래스
     * name : 사용자 이름
     * email : 사용자 이메일 (카카오톡의 경우 사용자 이메일 등록이 안되면 값이 없을 수 있다.)
     */
    @Data
    public static class KakaoAccount {
        private String name;
        private String email;
    }
}
