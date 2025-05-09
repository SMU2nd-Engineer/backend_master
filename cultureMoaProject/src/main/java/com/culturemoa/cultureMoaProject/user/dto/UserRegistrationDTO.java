package com.culturemoa.cultureMoaProject.user.dto;

import lombok.Data;

/**
 * UserRegistrationDTO
 * 회원가입 폼에 맞춘 DTO
 * id : 아이디
 * name : 이름
 * password : 패스워드
 * nickName : 닉네임
 * address : 주소
 * detailAddress : 상세 주소
 * emailLocal : 이메일 앞 부분
 * emailDomain : 이메일 뒷 부분
 * socialProvider : 소셜 로그인일 경우 어디 소셜인지
 */
@Data
public class UserRegistrationDTO {
    private String id;
    private String name;
    private String password;
    private String nickName;
    private String address;
    private String detailAddress;
    private String emailLocal;
    private String emailDomain;
    private String socialProvider;
}
