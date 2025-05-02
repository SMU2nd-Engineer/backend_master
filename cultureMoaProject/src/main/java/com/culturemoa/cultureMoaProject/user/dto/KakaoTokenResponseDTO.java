package com.culturemoa.cultureMoaProject.user.dto;
import lombok.Data;

/**
 * kakaoTokenResponseDTO
 * 카카오 토큰 정보를 역직렬화 해서 쉽게 받기 위한 DTO
 * token_type : 토큰 타입
 * access_token : 사용자 액세스 토큰 값
 * id_token : 	ID 토큰 값
 * expires_in : 액세스 토큰과 ID 토큰의 만료 시간(초)
 * refresh_token : 사용자 리프레시 토큰 값
 * refresh_token_expires_in : 리프레시 토큰 만료 시간(초)
 * scope : 인증된 사용자의 정보 조회 권한 범위, 범위가 여러 개일 경우, 공백으로 구분
 */
@Data
public class KakaoTokenResponseDTO {

    private String token_type;
    private String access_token;
    private String id_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String scope;
}
