package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 비밀 번호 찾기 위해서 전달한 json 받을 DTO
 * id : 사용자 입력한 id
 * email : 사요자가 입력한 이메일
 * name : 사용자가 입력한 이름
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindPasswordRequestDTO {
    private String id;
    private String name;
    private String email;
}
