package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 마이페이지 찜 목록에서 찜 목록을 삭제할 때 사용할 DTO
 * productIdx : 찜 목록에서 제거할 상품 idx
 * eDate : 삭제 요청 할 경우 날짜를 넣기 위한 edate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPickUpdateDTO {
    private int productIdx;
    private LocalDateTime eDate;
}
