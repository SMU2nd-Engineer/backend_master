package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인시 정보를 담을 공감.
 * id : 사용자 입력 받은 아이디 넣을 변수
 * password : 사용자 입력 받은 비밀번호 넣을 변수
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String id;
    private String password;
}
