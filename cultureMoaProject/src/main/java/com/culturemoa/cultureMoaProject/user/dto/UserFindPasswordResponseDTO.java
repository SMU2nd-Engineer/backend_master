package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀 번호 변경 후 전달할 DTO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindPasswordResponseDTO {
    private String message;
}
