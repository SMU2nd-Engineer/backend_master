package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 완료 후 전달용 DTO
 * 현재는 사용하지 않으나 확장성을 위하여 만들어 놓음
 * id : 사용자 조회 후 정보
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String id;
    private String nickName;
}
