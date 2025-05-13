package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 id 찾기 요청 DTO
 * name : 사용자 입력 이름
 * email : 사용자 입력 이메일
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindIdRequestDTO {
    private String name;
    private String email;
}
