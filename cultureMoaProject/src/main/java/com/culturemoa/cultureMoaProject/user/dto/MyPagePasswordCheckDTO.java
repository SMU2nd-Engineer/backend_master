package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지 개인 정보 조회시 비밀번호 체크용 DTO
 * password : 프론트에서 입력 받은 비밀번호 또는 db에서 찾은 패스워트 정보
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPagePasswordCheckDTO {
    private String password;
}
