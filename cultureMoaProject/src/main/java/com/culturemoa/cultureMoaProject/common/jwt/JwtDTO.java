package com.culturemoa.cultureMoaProject.common.jwt;

import com.culturemoa.cultureMoaProject.user.dto.LoginUserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  JwtDTO
 *  액세스 토큰 역직렬화 하기 위한 클래스
 *  accessToken : 액세스 토큰을 저장할 멤버 변수
 */
@Data
@AllArgsConstructor
public class JwtDTO {
    private String accessToken;
    private LoginUserInfoDTO userInfo;
}
