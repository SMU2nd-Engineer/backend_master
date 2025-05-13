package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * id 찾기 응답 DTO
 * id : 조회 된 사용자 id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindIdResponseDTO {
    private String id;
}
