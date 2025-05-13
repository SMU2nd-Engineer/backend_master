package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserWithdrawalDTO
 * 탈퇴할 사용자의 정보를 담을 DTO
 * id : 탈퇴할 사용자의 id
 * wDate : 탈퇴한 날짜
 * eDate : 탈퇴 일자 등록 및 수정을 위하여 추가
 * isWithdrawal : 탈퇴 여부 체크
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithdrawalDTO {
    private String id;
    private LocalDateTime wDate;
    private LocalDateTime eDate;
    private int isWithdrawal;
}
