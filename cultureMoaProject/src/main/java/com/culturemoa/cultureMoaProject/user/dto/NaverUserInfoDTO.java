package com.culturemoa.cultureMoaProject.user.dto;

import lombok.Data;

/**
 * NaverUserInfoDTO
 * 카카오 토큰에서 사용자 정보를 역직렬화로 쉽게 꺼내기 위해 생성한 DTO
 * response / id : 회원번호 - userId로 사용할 값
 */
@Data
public class NaverUserInfoDTO {
    private String resultcode;
    private String message;
    private Response response;

    /**
     * Response
     * json 객체 안에 json 객체 값을 가지고 오기 위한 내부 클래스
     * id : 사용자 고유 번호
     */
    @Data
    public static class Response {
        private String id;
    }
}
