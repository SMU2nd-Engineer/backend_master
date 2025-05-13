package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지 개인 정보 열람시 소셜 로그인 정보를 담을 DTO
 * socialLogin : 소셜 로그인 정보를 담을 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageCheckSocialDTO {
    private String socialLogin;
}
