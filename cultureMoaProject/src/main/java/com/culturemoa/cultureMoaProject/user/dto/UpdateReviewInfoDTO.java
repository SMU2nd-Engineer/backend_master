package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 리뷰 수정할 경우 넣을 값.
 * rating : 별점
 * reviewIdx : 리뷰 idx (리뷰 및 리뷰 평가 기록 테이블 조회용)
 * sellerIdx : 판매자 idx(리뷰 평가 테이블 조회용)
 * reviewText : 리뷰 텍스트
 * evaluation : 기존 평가 내역에서 변경할 리스트
 * addChangeValueEvaluation : 값 변경할 항목들과 값을 어떻게 변화할지 담은 변수
 * cDate : 수정 날짜
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewInfoDTO {
    private double rating;
    private int reviewIdx;
    private int sellerIdx;
    private String reviewText;
    private List<String> evaluation;
    private Map<String,Integer> changeValueEvaluation;
    private LocalDateTime cDate;
}
