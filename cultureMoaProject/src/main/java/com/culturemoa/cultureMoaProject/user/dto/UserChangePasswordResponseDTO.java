package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 변경 후 응답용 DTO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordResponseDTO {
    private String message;
}
