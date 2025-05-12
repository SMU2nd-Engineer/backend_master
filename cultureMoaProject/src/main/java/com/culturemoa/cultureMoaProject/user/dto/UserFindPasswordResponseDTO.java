package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀 번호 변경 클릭시 조회할 데이터를 담을 DTO
 * password : 단방향 암호화된 암호 파일을 담음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindPasswordResponseDTO {
    private String password;
}
