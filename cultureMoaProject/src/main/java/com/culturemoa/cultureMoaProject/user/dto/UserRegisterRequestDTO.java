package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserRegisterDTO
 * 회원 정보 등록 - 프론트에서 전달한 정보를 담을 DTO
 * id : 아이디
 * name : 이름
 * password : 패스워드
 * nickName : 닉네임
 * address : 주소
 * email : 이메일
 * sDate : 생성한 날짜 - serivce에서 생성
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDTO {
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String nickName;
    private String socialLogin;
    private LocalDateTime sDate;
}