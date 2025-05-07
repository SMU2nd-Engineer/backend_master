package com.culturemoa.cultureMoaProject.user.dto;
import lombok.Data;

/**
 * GoogleTokenResponseDTO
 * 구글 토큰 정보를 역직렬화 해서 쉽게 받기 위한 DTO
 * access_token : 애플리케이션에서 Google API 요청을 승인하기 위해 전송하는 토큰입니다.
 * expires_in : 액세스 토큰의 남은 전체 기간(초)입니다.
 * refresh_token : 새 액세스 토큰을 가져오는 데 사용할 수 있는 토큰입니다.
 * refresh_token_expires_in : 새로고침 토큰의 남은 전체 기간(초)입니다. 이 값은 사용자가 시간 기반 액세스를 부여할 때만 설정됩니다.
 * scope : access_token에서 부여한 액세스 범위로, 공백으로 구분되고 대소문자가 구분되는 문자열 목록으로 표현됩니다.
 * token_type : 반환된 토큰 유형입니다. 이때 이 필드의 값은 항상 Bearer로 설정됩니다.
 * id_token : jwt 토큰 (사용자 정보가 담겨옮) -> 사용자히 않음
 */
@Data
public class GoogleTokenResponseDTO {

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
