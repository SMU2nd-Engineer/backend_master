package com.culturemoa.cultureMoaProject.user.dto;

import lombok.Data;

/**
 * KakaoUserInfoDTO
 * 카카오 토큰에서 사용자 정보를 역직렬화로 쉽게 꺼내기 위해 생성한 DTO
 * id : 회원번호 - userId로 사용할 값
 */
@Data
public class GoogleUserInfoDTO {
    private String sub;
    private String email;
    private String name;

}
