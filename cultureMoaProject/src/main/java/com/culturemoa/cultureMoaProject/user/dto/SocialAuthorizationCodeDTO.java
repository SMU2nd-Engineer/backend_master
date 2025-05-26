package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 소셜 로그인 인가 코드를 위한 DTO
 * code : 소셜 로그인에서 받아온 코드
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialAuthorizationCodeDTO {
    private String code;
    private Boolean autoLogin;
}
