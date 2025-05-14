package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지에 넣을 사용자 정보를 담을 DTO
 * name : 이름
 * nickName : 닉네임
 * address : 주소
 * email : 이메일
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageGetUserInfoDTO {
    private String name;
    private String nickName;
    private String address;
    private String email;

}
