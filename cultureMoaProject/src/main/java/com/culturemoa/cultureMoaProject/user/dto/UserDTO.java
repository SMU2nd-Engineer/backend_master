package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserDTO
 * 회원 DTO, DB와 맞춤, 회원 정보 등록할 때 사용
 * id : 아이디
 * name : 이름
 * password : 패스워드
 * nickName : 닉네임
 * address : 주소
 * email : 이메일
 * social_login : 소셜 로그인 정보
 * sDate : 생성한 날짜
 * eDate : 수정한 날짜
 * wDate : 회원 탈퇴 날짜
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long idx;
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String nickName;
    private String socialLogin;
    private LocalDateTime sDate;
    private LocalDateTime eDate;
    private LocalDateTime cDate;

}
