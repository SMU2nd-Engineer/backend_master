package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 거래 평가 항목과 개수를 가져올 DTO
 * categorySubIdx 카테고리 서브 고유 번호
 * countEvaluation : 평가항목 별 개수
 * evaluationName : 평가 항목 이름
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageEvaluationDTO {
    private int categorySubIdx;
    private int countEvaluation;
    private String evaluationName;
}
