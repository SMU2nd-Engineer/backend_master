package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 소셜 로그인에 사용할 DTO
 * ID만 받아옴
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginResponseDTO {
    private String id;
}
