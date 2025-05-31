package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * peak 여부를 확인하기 위하여 상품에 정보를 전달 또는 찜으로 선택한 상품의 정보를 찜 테이블에 저장할 때 사용하기 위한 DTO
 * isPeak : select 를 통해서 담길 count 출력 값 0이면 peak이 아닌것이고 1이면 peak인것.
 * userId : 사용자 정보
 * idx : 프론트에서 받은 상품 idx
 * sDate : 등록 날짜
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPickInfoDTO {
    private int userIdx;
    private int isPick;
    private int idx;
    private LocalDateTime sDate;
}
