package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 후 전역 변수에 담을 유저 정보
 * userIdx :  로그인 유저 고유 번호
 * userName : 로그인 유저 이름
 * userNickName : 로그인 유저 닉네임
 * userId : 로그인 유저 아이디
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfoDTO {
    private int userIdx;
    private String userName;
    private String userNickName;
    private String userId;
}
