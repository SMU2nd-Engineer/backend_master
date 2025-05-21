package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 비밀번호 변경 요청 DTO
 * id : 변경할 사용자 id
 * password : 새롭게 전달 받은 password
 * cDate : 수정 사항 발생 날짜 저장
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequestDTO {
    private String id;
    private String password;
    private LocalDateTime cDate;
}
