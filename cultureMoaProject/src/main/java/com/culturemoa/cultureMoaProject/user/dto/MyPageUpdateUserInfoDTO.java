package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 마이페이지 개인 정보 수정에서 업데이트 된 정보를 담을 DTO
 * id : 토큰에서 추출할 id가 담길 변수
 * name : 프론트에서 받은 이름
 * password : 프론트에서 받은 비밀번호(null이거나 "" 일 수 있음)
 * nickName : 프론트에서 받은 닉네임
 * email : 프론트에서 받은 이메일
 * address : 프론트에서 받은 주소
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageUpdateUserInfoDTO {
    private String id;
    private String name;
    private String password;
    private String nickName;
    private String email;
    private String address;
    private LocalDateTime eDate;

}
