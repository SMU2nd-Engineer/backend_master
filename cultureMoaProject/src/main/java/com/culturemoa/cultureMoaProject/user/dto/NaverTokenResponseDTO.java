package com.culturemoa.cultureMoaProject.user.dto;
import lombok.Data;

/**
 * kakaoTokenResponseDTO
 * 네이버 토큰 정보를 역직렬화 해서 쉽게 받기 위한 DTO
 * access_token : 접근 토큰, 발급 후 expires_in 파라미터에 설정된 시간(초)이 지나면 만료됨
 * refresh_token : 갱신 토큰, 접근 토큰이 만료될 경우 접근 토큰을 다시 발급받을 때 사용
 * token_type : 접근 토큰의 타입으로 Bearer와 MAC의 두 가지를 지원
 * expires_in : 접근 토큰의 유효 기간(초 단위)
 * error : 에러 코드
 * error_description : 에러 메시지
 */
@Data
public class NaverTokenResponseDTO {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private String error;
    private String error_description;
}
